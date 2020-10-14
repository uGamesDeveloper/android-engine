package com.ugames.engine.coroutine;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;

public class Coroutine {

    private ArrayDeque<InstructionStruct> queue = new ArrayDeque<>();

    public Coroutine(@NonNull ICoroutine iCoroutine) {
        iCoroutine.run(this);
    }

    public static void startCoroutine(@NonNull Coroutine coroutine) {
        coroutine.run();
    }

    public void main(MainInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.main));
    }

    public void yield(YieldInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.yield));
    }

    private void run() {
        if (queue.size() > 0) {
            InstructionStruct instructionStruct = queue.pop();
            Instruction instruction = instructionStruct.getInstruction();
            if (instructionStruct.getTypeInstruction() == TypeInstruction.main) {
                if (instruction != null)
                    ((MainInstruction) instruction).run();
                run();
            } else if(instructionStruct.getTypeInstruction() == TypeInstruction.yield){
                if (instruction != null)
                    ((YieldInstruction) instruction).run(this);
                else {
                    run();
                }
            } else {
                throw new IllegalArgumentException("Not current type instruction yet.");
            }
        }
    }

    public void complete() {
        run();
    }


    private enum TypeInstruction {
        main,
        yield
    }

    private static class InstructionStruct {
        Instruction instruction;
        TypeInstruction typeInstruction;

        public InstructionStruct(Instruction instruction, TypeInstruction typeInstruction) {
            this.instruction = instruction;
            this.typeInstruction = typeInstruction;
        }

        public Instruction getInstruction() {
            return instruction;
        }

        public TypeInstruction getTypeInstruction() {
            return typeInstruction;
        }
    }
}
