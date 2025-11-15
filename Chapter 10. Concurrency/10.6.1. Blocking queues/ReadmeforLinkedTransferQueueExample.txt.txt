# LinkedTransferQueue — Immediate Handoff vs. Queueing Behavior

This file explains and demonstrates the two different behaviors of `LinkedTransferQueue.transfer()` depending on whether a consumer is already waiting.

---

## Case 1 — Consumer Is Already Waiting (Immediate Handoff)

When a consumer is blocked in `take()` at the moment `transfer()` is called:

* The element is **not** added to the queue.
* It is immediately passed to the waiting consumer.
* The producer returns instantly.

This shows the synchronous handoff behavior.

### Sequence

1. Consumer starts and calls `take()`, blocking.
2. Producer calls `transfer("X")`.
3. The consumer receives `X` immediately.
4. The producer unblocks.

---

## Case 2 — No Consumer Waiting (Queue + Producer Blocks)

If no consumer is currently blocked inside `take()`:

* The transferred element is inserted at the **tail** of the queue.
* The producer **blocks** until a consumer eventually takes the element.

### Sequence

1. Queue is preloaded with items: `A`, `B`, `C`.
2. Producer calls `transfer("Y")`.
3. No consumer is waiting → `Y` is added to the tail.
4. The producer blocks.
5. Consumer arrives later and removes: `A`, `B`, `C`, then `Y`.
6. Producer unblocks after `Y` is taken.

---

## Summary Table

| Situation                | transfer() Behavior | Producer Blocks? |
| ------------------------ | ------------------- | ---------------- |
| Consumer already waiting | Direct handoff      | No               |
| No waiting consumer      | Item added to queue | Yes              |

---

If you'd like, I can add:

* The full runnable Java example
* A diagram demonstrating message flow
* A shortened or extended version of this file
