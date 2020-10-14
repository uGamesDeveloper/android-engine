package com.ugames.engine.coroutine;

public interface YieldInstruction extends Instruction {
    void run(Coroutine coroutine);
}
