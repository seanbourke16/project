package project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {
    Map<Integer, Instruction> IMAP = new TreeMap<Integer, Instruction>();
    private CPU cpu;
    private Memory memory;
    private HaltCallback callback;
    private Job[] jobs = new Job[4];
    private Job currentJob;
    Code c = new Code();
    public MachineModel(){
	this(() -> System.exit(0));
    }
    public MachineModel(HaltCallback callback){
	this.callback=callback;
	//INSTRUCTION MAP entry for "NOP"
	IMAP.put(0x0, (arg, level) -> {
		cpu.incrPC();
	    });
	//INSTRUCTION MAP entry for "LOD"
	IMAP.put(0x1, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in LOD instruction");
		}
		if(level > 0) {
		    IMAP.get(0x1).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setAccum(arg);
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "STO"
	IMAP.put(0x2, (arg, level) -> {
		if(level < 1 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in STO instruction");
		}
		if(level > 1) {
		    IMAP.get(0x2).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    memory.setData(arg, cpu.getAccum());
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "ADD"
	IMAP.put(0x3, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in ADD instruction");
		}
		if(level > 0) {
		    IMAP.get(0x3).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setAccum(cpu.getAccum() + arg);
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "SUB"
	IMAP.put(0x4, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in SUB instruction");
		}
		if(level > 0) {
		    IMAP.get(0x4).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setAccum(cpu.getAccum() - arg);
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "MUL"
	IMAP.put(0x5, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in MUL instruction");
		}
		if(level > 0) {
		    IMAP.get(0x5).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setAccum(cpu.getAccum() * arg);
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "DIV"
	IMAP.put(0x6, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in DIV instruction");
		}
		if(arg==0){
		    throw new DivideByZeroException("Can't Divide by zero");
		}
		if(level > 0) {
		    IMAP.get(0x6).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setAccum(cpu.getAccum() / arg);
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "AND"
	IMAP.put(0x7, (arg, level) -> {
		if(level < 0 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in DIV instruction");
		}
		if(arg==0){
		    throw new DivideByZeroException("Can't Divide by zero");
		}
		if(level > 0) {
		    IMAP.get(0x7).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    if(cpu.getAccum()==0&&arg!=0){
			cpu.setAccum(1);
		    }
		    else{
			cpu.setAccum(0);
		    }
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "NOT"
	IMAP.put(0x8, (arg, level) -> {
		if(cpu.getAccum()==0){
		    cpu.setAccum(1);
		}
		else{
		    cpu.setAccum(0);
		}
		cpu.incrPC();
	    });
	//INSTRUCTION MAP entry for "CMPL"
	IMAP.put(0x9, (arg, level) -> {
		if(level < 1 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in STO instruction");
		}
		if(level > 1) {
		    IMAP.get(0x9).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    if(memory.getData(cpu.getMemBase()+arg)<0){
			cpu.setAccum(1);
		    }
		    else{
			cpu.setAccum(0);
		    }
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "CMPZ"
	IMAP.put(0xA, (arg, level) -> {
		if(level < 1 || level > 2) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in STO instruction");
		}
		if(level > 1) {
		    IMAP.get(0xA).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    if(memory.getData(cpu.getMemBase()+arg)==0){
			cpu.setAccum(1);
		    }
		    else{
			cpu.setAccum(0);
		    }
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "JUMP"
	IMAP.put(0xB, (arg, level) -> {
		if(level < 1 || level > 3) {
		    throw new IllegalArgumentException(
						       "Illegal indirection level in STO instruction");
		}
		if(level > 0) {
		    IMAP.get(0xB).execute(memory.getData(cpu.getMemBase()+arg), level-1);
		} else {
		    cpu.setpCounter(cpu.getpCounter()+arg);
		}
	    });
	//INSTRUCTION MAP entry for "JMPZ"
	IMAP.put(0xA, (arg, level) -> {
		if(cpu.getAccum()==0){
		    cpu.setpCounter(cpu.getpCounter()+arg);
		}
		else{
		    cpu.incrPC();
		}
	    });
	//INSTRUCTION MAP entry for "HALT"
	IMAP.put(0xF, (arg, level) -> {
		callback.halt();			
	    });
	
	jobs = new Job[4];
	currentJob=jobs[0];
	for (int i=0; i<4; i++){
	    jobs[i].setId(i);
	    jobs[i].setStartcodeIndex(i*Code.CODE_MAX/4);
	    jobs[i].setStartmemoryIndex(i*Memory.DATA_SIZE/4);
	}
    }
    public int getAccum() {
	return cpu.getAccum();
    }
    public void setAccum(int accum) {
	cpu.setAccum(accum);
    }
    public int getpCounter() {
	return cpu.getpCounter();
    }
    public void setpCounter(int pCounter) {
	cpu.setpCounter(pCounter);
    }
    public int getMemBase() {
	return cpu.getMemBase();
    }
    public void setMemBase(int memBase) {
	cpu.setMemBase(memBase);
    }
    public void incrPC() {
	cpu.incrPC();
    }
    public int getData(int index) {
	return memory.getData(index);
    }
    public void setData(int index, int value) {
	memory.setData(index, value);
    }
    public Instruction getInstr(int arg){
	return IMAP.get(arg);
    }
	
    public void setCode(int index, int op, int indirLvl, int arg){
	c.setCode(index, op, indirLvl, arg);
    }
    public Code getCode(){
	return c;
    }
    public int[] getData(){
	return memory.getData();
    }
    public int getChangedIndex(){
	return memory.getChangedIndex();
    }
    public Job getCurrentJob() {
	return currentJob;
    }
    public void changeToJob(int i){
	if (!(0<=i && i<= 3)){
	    throw new IllegalArgumentException("i is not within the range 0...3");
	} else if (i==currentJob.getId()){
			
	} else {
	    currentJob.setCurrentAcc(cpu.getAccum());
	    currentJob.setCurrentPC(cpu.getpCounter());
	    currentJob=jobs[i];
	    cpu.setAccum(currentJob.getCurrentAcc());
	    cpu.setpCounter(currentJob.getCurrentPC());
	    cpu.setMemBase(currentJob.getStartmemoryIndex());
	}
		
    }
    public States getCurrentState(){
	return currentJob.getCurrentState();
    }
    public void setCurrentState(States currentState){
	currentJob.setCurrentState(currentState);
    }
    public void clearJob(){
	memory.clear(currentJob.getStartcodeIndex(),currentJob.getStartcodeIndex()+currentJob.getCodeSize());
	setAccum(0);
	setpCounter(currentJob.getStartcodeIndex());
	currentJob.reset();
    }
    public void step(){
	try{
	    int pc=getpCounter();
	    if(pc<currentJob.getStartcodeIndex()||pc>=currentJob.getStartcodeIndex()+currentJob.getCodeSize()){
		throw new CodeAccessException("Program counter is out of bounds");
	    }
	    IMAP.get(c.getOp(pc)).execute(c.getArg(pc),c.getIndirLvl(pc));
	}
	catch(Exception e){
	    callback.halt();
	    throw e;
	}
    }
}
