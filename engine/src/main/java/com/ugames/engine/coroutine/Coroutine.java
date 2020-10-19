package com.ugames.engine.coroutine;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.ugames.engine.coroutine.instructions.YieldInstruction;
import com.ugames.engine.refs.Out;
import com.ugames.engine.refs.Ref;

import java.util.ArrayDeque;

public class Coroutine {

    private ArrayDeque<InstructionStruct> queue = new ArrayDeque<>();
    private Activity activity;

    private boolean isPlaying;

    public Coroutine(@NonNull ICoroutine iCoroutine) {
        isPlaying = true;
        iCoroutine.run(this);

    }

    public static void startCoroutine(@NonNull Coroutine coroutine, Activity activity) {
        coroutine.activity = activity;
        coroutine.run();
    }

    public static void stopCoroutine(@NonNull Coroutine coroutine) {
        coroutine.stopCoroutine();
    }

    public static void breakCoroutine(@NonNull Coroutine coroutine) {
        coroutine.coroutineBreak();
    }

    public static void resumeCoroutine(@NonNull Coroutine coroutine) {
        coroutine.resumeCoroutine();
    }

    public void main(YieldInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.main));
    }

    public void yield(YieldInstruction yieldInstruction) {
        queue.addLast(new InstructionStruct(yieldInstruction, TypeInstruction.yield));
    }

    private void run() {
        if(isPlaying) {
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
                }
                else {
                    throw new IllegalArgumentException("Not current type instruction yet.");
                }
            }
        }
    }

    private void stopCoroutine() {
        isPlaying = false;
    }

    private void resumeCoroutine() {
        isPlaying = true;
        run();
    }

    public void coroutineBreak() {
        isPlaying = false;
        queue.clear();
    }

    public void complete() {
        activity.runOnUiThread(this::run);
    }

    private enum TypeInstruction {
        main,
        yield,
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
