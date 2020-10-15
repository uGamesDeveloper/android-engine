package com.ugames.engine.coroutine;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.ugames.engine.coroutine.instructions.YieldInstruction;

import java.util.ArrayDeque;

public class Coroutine {

    private ArrayDeque<InstructionStruct> queue = new ArrayDeque<>();
    private Activity activity;

    public Coroutine(@NonNull ICoroutine iCoroutine) {
        iCoroutine.run(this);
    }

    public static void startCoroutine(@NonNull Coroutine coroutine, Activity activity) {
        coroutine.activity = activity;
        coroutine.run();
    }

    public static void stopCoroutine(@NonNull Coroutine coroutine) {
        coroutine.stopCoroutine();
    }

    public void main(YieldInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.main));
    }

    public void yield(YieldInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.yield));
    }

    private void run() {
        if (queue.size() > 0) {
            InstructionStruct instructionStruct = queue.pop();
            YieldInstruction instruction = instructionStruct.getInstruction();
            if (instructionStruct.getTypeInstruction() == TypeInstruction.main) {
                if (instruction != null)
                    instruction.run();
                run();
            } else if (instructionStruct.getTypeInstruction() == TypeInstruction.yield) {
                if (instruction != null)
                    instruction.run();
                else {
                    run();
                }
            } else if(instructionStruct.getTypeInstruction() == TypeInstruction.coroutineBreak) {
                stopCoroutine();
            }
            else {
                throw new IllegalArgumentException("Not current type instruction yet.");
            }
        }
    }

    private void stopCoroutine() {
        queue.clear();
    }

    public void coroutineBreak() {
        queue.addLast(new InstructionStruct(null, TypeInstruction.coroutineBreak));
    }

    public void complete() {
        activity.runOnUiThread(this::run);
    }

    private enum TypeInstruction {
        main,
        yield,
        coroutineBreak
    }

    private static class InstructionStruct {
        YieldInstruction instruction;
        TypeInstruction typeInstruction;

        public InstructionStruct(YieldInstruction instruction, TypeInstruction typeInstruction) {
            this.instruction = instruction;
            this.typeInstruction = typeInstruction;
        }

        public YieldInstruction getInstruction() {
            return instruction;
        }

        public TypeInstruction getTypeInstruction() {
            return typeInstruction;
        }
    }
}
