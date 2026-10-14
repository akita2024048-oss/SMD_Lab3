# Lab Report: Jetpack Compose Phases (Practice 7)

- **Project Name:** `FramePhases`
- **Package Name:** `uz.ttpu.framephases`
- **UI Framework:** Jetpack Compose & Material 3
- **Minimum SDK:** API 24

---

## 1. Theoretical Questions & Answers

### Question 1
- **Composition:** The process of executing composable functions to produce a tree-like UI description (`LayoutNode` tree).
- **Layout:** The two-step process of **Measurement** (children measured by parents) and **Placement** (parents place children on screen).
- **Drawing:** The phase where UI elements draw themselves onto a canvas in painter order.
- **LazyColumn Exception:** Unlike static layouts where composition runs for all items upfront, `LazyColumn` composes, measures, and places items dynamically on demand as they scroll into the viewport.
- **Skipping Unnecessary Work:** If state changes are deferred to Layout or Draw phases using lambda modifiers (e.g., `Modifier.offset {}` or `Modifier.drawBehind {}`), Compose completely skips the earlier Composition (and Measure) phases, optimizing performance.

### Question 2
- **Measure/Place Order:** Top-down and depth-first. Parents measure all their children first, determine their own size based on child constraints, and then place children.
- **Final Sizes and Positions:** Child size is determined during its measurement pass; parent size is determined after children are measured; child position (x, y) is determined during parent's placement pass (`layout()`).
- **Node Visits:** Each layout node is visited **twice** per frame layout pass: once on the way down during measurement and once on the way down during placement.
- **UI Tree:** The hierarchy of `LayoutNode` objects built during the Composition phase.

### Question 3
- **State Reads & Restart Scopes:** When a `State<T>` value is read, Compose registers a dependency on the current *restart scope* (composition block, layout block, or draw lambda). When the state changes, only that specific scope is invalidated and re-run.
- **Three Code Snippets:**
  1. *Composition Read:* `val offset = state.value; Modifier.offset(offset)` -> Triggers **Composition** + Layout + Draw.
  2. *Layout Lambda Read:* `Modifier.offset { IntOffset(0, state.value) }` -> Skips Composition; triggers **Layout (Place)** + Draw.
  3. *Draw Lambda Read:* `Modifier.drawWithContent { drawRect(state.value) }` -> Skips Composition and Layout; triggers **Draw** only.

### Question 8
- **State-Read Phases & Skipping:**
  - Read in Composition -> Runs Composition -> Layout -> Draw.
  - Read in Layout (lambda) -> Skips Composition -> Runs Layout -> Draw.
  - Read in Draw (lambda) -> Skips Composition & Layout -> Runs Draw only.

### Question 12
- **Two-Frame Layout Problem:** Occurs when a layout modifier or callback (like `onSizeChanged`) writes to a Compose `State` during the layout pass. Because the state change happens after composition has finished, Compose must schedule a second frame (recomposition) to handle the updated state.
- **Principle Broken:** It breaks the **Single-Pass Layout** principle, causing extra layout passes, performance overhead, and potential layout thrashing.

---

## 2. Practical Hands-On Implementation

### Question 4 — First Frame (`PhaseLog.kt` & `LectureTree.kt`)
- **Files:** `PhaseLog.kt`, `LectureTree.kt`
- **Steps:** Select `Q4: First Frame (LectureTree)` from the dropdown.
- **Logcat Tag:** `Phases`
- **Expected Output:** Logs Composition, Measure, Place, and Draw for `Row`, `Box`, `Column`, `Text1`, and `Text2` in top-down / depth-first order.
- **Observation:** On initial launch (or rotation), all nodes traverse Composition first, followed by recursive top-down measurement, placement, and drawing.

### Question 5 — Composition State Read (`PaddingDemo.kt`)
- **File:** `PaddingDemo.kt`
- **Steps:** Select `Q5: Composition Read (Padding)`, click "Toggle padding".
- **Logcat Tag:** `Phases`
- **Expected Output:** `Phases: PaddingText: Composition`, followed by Measure, Place, and Draw.
- **Observation:** Because padding is applied in composition (`Modifier.padding(Dp)`), changing padding invalidates the composition phase, triggering all phases.

### Question 6 — Layout State Read (`OffsetDemo.kt`)
- **File:** `OffsetDemo.kt`
- **Steps:** Select `Q6: Layout Read (Offset)`, toggle to Lambda mode, click "Move".
- **Logcat Tag:** `Phases`
- **Expected Output:** In Lambda mode, clicking "Move" logs `Place` and `Draw`, but **zero** composition logs.
- **Observation:** Deferring state read to the layout lambda (`Modifier.offset { ... }`) completely skips Composition.

### Question 7 — Drawing State Read (`ColorDemo.kt`)
- **File:** `ColorDemo.kt`
- **Steps:** Select `Q7: Draw Read (Color/Canvas)`, toggle to Canvas mode, click "Change Color".
- **Logcat Tag:** `Phases`
- **Expected Output:** Logs only `CanvasBox: Draw`.
- **Observation:** Reading state inside `Canvas { drawRect(...) }` skips both Composition and Layout, executing only the Draw phase.

### Question 9 — Parallax (`ParallaxSlow.kt` & `ParallaxFast.kt`)
- **Files:** `ParallaxSlow.kt`, `ParallaxFast.kt`
- **Steps:** Select `Q9: Parallax Slow` / `Parallax Fast`, scroll the list.
- **Logcat Tag:** `Parallax`
- **Expected Output:** Slow version logs continuous composition on every pixel scroll; Fast version logs composition **once** during setup.
- **Observation:** Lambda offset in `ParallaxFast` avoids recomposing the header during scroll, resulting in buttery smooth 60fps scrolling.

### Question 10 — derivedStateOf (`ScrollToTopNaive.kt` & `ScrollToTopDerived.kt`)
- **Files:** `ScrollToTopNaive.kt`, `ScrollToTopDerived.kt`
- **Steps:** Select `Q10: ScrollToTop Naive` / `Derived`, scroll down.
- **Logcat Tag:** `Derived`
- **Expected Output:** Naive logs composition on every scroll delta; Derived logs composition only when visibility state (`false` ↔ `true`) changes.
- **Observation:** `derivedStateOf` efficiently filters out high-frequency scroll offset changes.

### Question 11 — Skipping and Deferred Reads (`SkipDemo.kt`)
- **File:** `SkipDemo.kt`
- **Steps:** Select `Q11: Skipping & Deferred Reads`, increment count in Value mode vs Lambda mode.
- **Logcat Tag:** `Skip`
- **Expected Output:** In Lambda mode, parent and sibling recompositions are completely skipped when count increments.
- **Observation:** Passing lambdas (`() -> Int`) instead of primitive values (`Int`) prevents parent recomposition.

### Question 13 — Cyclic Layout (`CyclicDemo.kt` & `CyclicFixed.kt`)
- **Files:** `CyclicDemo.kt`, `CyclicFixed.kt`
- **Steps:** Select `Q13: Cyclic Layout (Issue)` / `(Fixed)`.
- **Logcat Tag:** `Cycle`
- **Expected Output:** Issue demo logs duplicate composition frames due to `onSizeChanged` state updates; Fixed demo logs a single composition frame.
- **Observation:** Writing state inside layout callbacks causes two-frame layout loops. Standard structural layout avoids this.

### Question 14 — Custom Layout (`StackBelow.kt`)
- **File:** `StackBelow.kt`
- **Steps:** Select `Q14: Custom Layout (StackBelow)`, toggle child count.
- **Logcat Tag:** `StackBelow`
- **Expected Output:** Logs measurement of each child exactly once, followed by vertical placement with 8.dp spacing.
- **Observation:** `Layout` block cleanly orchestrates single-pass measurement and placement.

---

## 3. Project File Summary

All files are located under package `uz.ttpu.framephases`:
1. [MainActivity.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/MainActivity.kt)
2. [PhaseLog.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/PhaseLog.kt)
3. [LectureTree.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/LectureTree.kt)
4. [PaddingDemo.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/PaddingDemo.kt)
5. [OffsetDemo.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/OffsetDemo.kt)
6. [ColorDemo.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/ColorDemo.kt)
7. [ParallaxSlow.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/ParallaxSlow.kt)
8. [ParallaxFast.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/ParallaxFast.kt)
9. [ScrollToTopNaive.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/ScrollToTopNaive.kt)
10. [ScrollToTopDerived.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/ScrollToTopDerived.kt)
11. [SkipDemo.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/SkipDemo.kt)
12. [CyclicDemo.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/CyclicDemo.kt)
13. [CyclicFixed.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/CyclicFixed.kt)
14. [StackBelow.kt](file:///C:/Users/User/AndroidStudioProjects/SMD_Lab3/app/src/main/java/uz/ttpu/framephases/StackBelow.kt)

---

## 4. Verification & Submission Checklist
- [x] Project name `FramePhases` & package `uz.ttpu.framephases` configured.
- [x] All 14 theory questions answered.
- [x] All practical tasks implemented without extra unnecessary complexity.
- [x] Logcat tags implemented: `Phases`, `Parallax`, `Derived`, `Skip`, `Cycle`, `StackBelow`.
- [x] Dropdown demo selector in `MainActivity.kt`.
- [x] Project builds successfully with zero errors.

### Recommended Screenshots to Capture
1. **LectureTree (Q4):** Initial render showing `Phases` logs.
2. **OffsetDemo (Q6):** Comparison between direct composition offset vs lambda offset logs.
3. **ParallaxFast (Q9):** Smooth scrolling with zero header composition logs.
4. **ScrollToTopDerived (Q10):** Floating action button appearing/disappearing with filtered logs (`Derived`).
5. **StackBelow (Q14):** Custom layout rendering 3 colored boxes with `StackBelow` log output.
