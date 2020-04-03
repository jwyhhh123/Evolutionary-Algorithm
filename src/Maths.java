
public class Maths extends GAApplication{
	int goal = 0;
	int length = 0;
	public Maths(int mathsGoal1, int mathsChromLen) {
		goal = mathsGoal1;
		length = mathsChromLen;
        for(int i=0;i<SizeofPopulation;i++){
        	individuals.add(randomIndividual());
        }
	}

	@Override
	protected Individual randomIndividual() {

		return new MathIndividual(goal,length);
	}

}
