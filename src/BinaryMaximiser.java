
public class BinaryMaximiser extends GAApplication {

    public int length;

    public BinaryMaximiser(int ChromosomeLength){
        length = ChromosomeLength;
        for(int i=0;i<SizeofPopulation;i++){
        	individuals.add(randomIndividual());
        }
    }

	@Override
	protected Individual randomIndividual() {

    	return new BinaryIndividual(length);
	}

}
