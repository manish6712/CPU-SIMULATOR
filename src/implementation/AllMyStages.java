/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import implementation.AllMyLatches.*;
import utilitytypes.EnumOpcode;
import baseclasses.InstructionBase;
import baseclasses.PipelineRegister;
import baseclasses.PipelineStageBase;
import voidtypes.VoidLatch;
import baseclasses.CpuCore;

/**
 * The AllMyStages class merely collects together all of the pipeline stage 
 * classes into one place.  You are free to split them out into top-level
 * classes.
 * 
 * Each inner class here implements the logic for a pipeline stage.
 * 
 * It is recommended that the compute methods be idempotent.  This means
 * that if compute is called multiple times in a clock cycle, it should
 * compute the same output for the same input.
 * 
 * How might we make updating the program counter idempotent?
 * 
 * @author
 */
public class AllMyStages {
    /*** Fetch Stage ***/
    static class Fetch extends PipelineStageBase<VoidLatch,FetchToDecode> {
        public Fetch(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }
        
        @Override
        public String getStatus() {
            // Generate a string that helps you debug.
            return null;
        }

        @Override
        public void compute(VoidLatch input, FetchToDecode output) {
            GlobalData globals = (GlobalData)core.getGlobalResources();
            int pc = globals.program_counter;
            // Fetch the instruction
            InstructionBase ins = globals.program.getInstructionAt(pc);
            if (ins.isNull()) return;


            public void process() {
                if(control.isPipelineStalled) return;
                InstructionBase ins = globals.program.getInstructionAt(pc);
                pc.write(pc);

                if(instruction != null)
                    pc++;
            }

            @Override
            public void clearStage() {
                pc = Constants.STRT_INST_ADDRESS;
                instruction = null;
            }

            public void clearStage(Long newFetchAdd, boolean isOffset){
                if(isOffset)
                    pc = control.getDecode().pc.read() + newFetchAdd;
                else
                    pc = newFetchAdd;
                instruction = null;
            }

        
        @Override
        public boolean stageWaitingOnResource() {
            // Hint:  You will need to implement this for when branches
            // are being resolved.
            return false;
        }
        
        
        /**
         * This function is to advance state to the next clock cycle and
         * can be applied to any data that must be updated but which is
         * not stored in a pipeline register.
         */
        @Override
        public void advanceClock() {
        }public PipelineStageBase(CpuCore core, PipelineRegister input, PipelineRegister output) {
                this.core = core;
                this.input_reg = input;
                this.output_reg = output;
            }
        }

    }

    
    /*** Decode Stage ***/
    static class Decode extends PipelineStageBase<FetchToDecode,DecodeToExecute> {
        public Decode(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }
        
        @Override
        public boolean stageWaitingOnResource() {
            return false;
        }
        

        @Override
        public void compute(FetchToDecode input, DecodeToExecute output) {
            InstructionBase ins = input.getInstruction();

            ins = ins.duplicate();
            currently_doing = null;

            input = input_reg.read();
            if (!input.getInstruction().isNull()) {
                currently_doing = input.getInstruction().toString();
            }


            output = output_reg.newLatch();


            compute(input, output);

            if (currently_doing == null) {
                currently_doing = output.getInstruction().toString();
            }

            if (stageWaitingOnResource()) {

                output_reg.write(output_reg.invalidLatch());
            } else {

                output_reg.write(output);
            }
        }

            if (ins.isNull()) return;
            
            GlobalData globals = (GlobalData)core.getGlobalResources();
            int[] regfile = globals.register_file;

           public Decode(Control control) {
            pc = new Latch(control);
            control.addProcessListener(this);
            this.control = control;
        }
           public void process() {
            if(control.isPipelineStalled) return;
            try {
                instruction = control.getFetch().instruction;
                pc.write(control.getFetch().pc.read());
                readSources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            output.setInstruction(ins);
            // Set other data that's passed to the next stage.
        }
    }
    

    /*** Execute Stage ***/
    static class Execute extends PipelineStageBase<DecodeToExecute,ExecuteToMemory> {
        public Execute(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(DecodeToExecute input, ExecuteToMemory output) {
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            int source1 = ins.getSrc1().getValue();
            int source2 = ins.getSrc2().getValue();
            int oper0 =   ins.getOper0().getValue();

            int result = MyALU.execute(ins.getOpcode(), source1, source2, oper0);

            public void process() {

                if(control.isPipelineStalled){
                    instruction = null;
                    return;
                }
                pc.write(control.getOpcode().pc.read());
                control.getOpcode().readSources();
                instruction = control.getOpcode().instruction;
                if(instruction != null){
                   
                     output.setInstruction(ins);
                     public static boolean needsWriteback(EnumOpcode op) {
                         return writebackSet.contains(op);
                     }
                     
                     public boolean needsWriteback() {
                         return writebackSet.contains(this);
                     }
        }
    }
    

    /*** Memory Stage ***/
    static class Memory extends PipelineStageBase<ExecuteToMemory,MemoryToWriteback> {
        public Memory(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(ExecuteToMemory input, MemoryToWriteback output) {
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            public Memory(int input1)
            {
                Integer tmp = new Integer(0);

                for (int i = 1; i < input1; i++){
                    _memory.add(tmp);
                }
            }

	public Memory(int input1, int Input2)
            {
                this(input1);
                char[] input = Input2.toCharArray();
                for(int i = 0; i < Input2.length(); i++)
                {
                    setValueAt(i, (int) input[i]);
                }
            }

            output.setInstruction(ins);
            // Set other data that's passed to the next stage.
        }
    }
    

    /*** Writeback Stage ***/
    static class Writeback extends PipelineStageBase<MemoryToWriteback,VoidLatch> {
        public Writeback(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(MemoryToWriteback input, VoidLatch output) {
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            private functionalUnits.WriteBackUnit writeBack;

               private WriteBackStage()
            {
                super();

                this.stageType = StageType.WBSTAGE;

                writeBack = functionalUnits.WriteBackUnit.getInstance();
            }




            if (input.getInstruction().getOpcode() == EnumOpcode.HALT) {

            }
        }
    }
}
