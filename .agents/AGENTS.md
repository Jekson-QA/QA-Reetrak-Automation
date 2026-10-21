# Project-Wide Instruction Set

These instructions serve as global guidelines and rules for all future tasks within this project. They apply to every prompt, task, code change, modification, and implementation within this project unless explicitly overridden by a later instruction.

## Global Instructions

Before responding to or executing any prompt:

1. **Review and Follow Rules First**: Review and follow these project-wide instructions first.
2. **Explain Before Implementing**: Do not implement changes directly. Only explain what changes are required and where those changes should be made.
3. **Understand Context**: Understand the existing project structure, architecture, coding standards, and conventions before making any changes.
4. **Minimal Changes**: Make only the minimum required changes to fulfill the requested requirement.
5. **Reuse Existing Code**: Reuse existing methods, utilities, components, and functionality whenever possible. Do not create duplicate implementations.
6. **Maintain Consistency**: Maintain consistency with the existing codebase, including naming conventions, folder structure, coding style, and design patterns.
7. **Preserve Working Code**: Do not modify unrelated code or functionality. Preserve all existing working functionality unless the requirement explicitly requires a change.
8. **Avoid Unnecessary Dependencies**: Do not introduce new libraries, frameworks, dependencies, or unnecessary complexity unless they are genuinely required.
9. **Impact Analysis**: Analyze the requirement and its impact on the existing codebase before implementation. Consider backward compatibility and potential regression risks.
10. **Clarify Ambiguities**: If the requirement is unclear or critical information is missing, identify the ambiguity instead of making assumptions.
11. **Clean Code Principles**: Keep all implementations simple, readable, reusable, maintainable, and scalable. Follow DRY (Don't Repeat Yourself) and appropriate clean-code principles.
12. **Automation Best Practices**: For automation-related tasks, prioritize reusable helper methods and utilities, stable locators defined specifically according to each page (page-wise locators), proper waits, maintainable test-data handling, and reliable test execution.
13. **Verification**: Before finalizing any task, verify that the implementation follows these instructions, fulfills the requirement, preserves existing functionality, and does not introduce unnecessary changes.


## Priority

These instructions apply to every prompt, task, code change, modification, and implementation within this project, unless a later instruction explicitly overrides a specific instruction.

Always consider the existing project context and these global instructions before acting on any individual prompt.

## Product Master Level Hierarchy Rules

When adding or updating products in Product Master, strictly adhere to the following business rules:

1. **Allowed Level Combinations**:
   - **Single-Level (3)**: `Level-1`, `Level-2`, `Level-3`
   - **Two-Level (7)**: `1-2`, `1-6`, `2-3`, `2-6`, `3-4`, `3-6`, `4-6`
   - **Three-Level (5)**: `1-2-3`, `1-2-6`, `2-3-4`, `2-3-6`, `3-4-6`
   - **Four-Level (3)**: `1-2-3-4`, `1-2-3-6`, `2-3-4-6`

2. **Level Transition / Delta Rule**:
   - Product updates allow modifying level count by **only $\pm 1$ level at a time** (cannot jump $\pm 2$ levels or more).

3. **Dynamic Dropdown Filtering**:
   - **Increasing Level (+1)**: Dropdown shows *only* the specific level(s) that can be added to form a valid higher combination.
   - **Decreasing Level (-1)**: Dropdown shows *only* the specific level(s) that can be removed to form a valid lower combination.

