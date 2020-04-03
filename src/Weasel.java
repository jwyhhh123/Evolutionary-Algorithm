
public class Weasel extends GAApplication{
	String goal;
	
	public Weasel(String weaselGoal1) {
		this.goal = weaselGoal1;
        for(int i=0;i<SizeofPopulation;i++){
        	individuals.add(randomIndividual());
        }
	}

	@Override
	protected Individual randomIndividual() {

		return new WeaselIndividual(goal);
	}

}

