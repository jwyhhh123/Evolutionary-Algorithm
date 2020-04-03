
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public abstract class GAApplication {

    public int SizeofPopulation;
    public ArrayList<Individual> individuals = new ArrayList<>();

    GAApplication() {
        this.SizeofPopulation = 200;
    }

    // generate individuals, it is abstract due to the differences of Individual types.
    protected abstract Individual randomIndividual();

    public void run() {
        //2. mutate, applied to the whole individuals.
        double mutateProbility = 0.3;
        for(int i=0;i<individuals.size();i++){
            if (Math.random() <= mutateProbility){
                individuals.get(i).mutate();
            }
        }

        //3. select parents
        int parentNum = (int)(SizeofPopulation * 0.40);
        Collections.sort(individuals);
        ArrayList<Individual> parents = new ArrayList<>();
        for(int i=0;i<parentNum;i++){
            parents.add(individuals.get(i));
        }

        //4. create new childen
        Random rn = new Random();
        Random rn2 = new Random();
        int childsNum = (int)(SizeofPopulation * 0.40);
        double crossProbility = 0.5;
        for(int i=0;i<childsNum;i++){
            int par1 = rn.nextInt(parentNum);
            int par2 = rn2.nextInt(parentNum);

            if(par1 != par2) {
                    Individual parent1 = parents.get(par1);
                    Individual parent2 = parents.get(par2);

                    Individual child1 = parent1;
                    Individual child2 = parent2;
                    if (Math.random() <= crossProbility) {
                        child1 = parent1.crossover(parent2);
                        child2 = parent2.crossover(parent1);
                    }
                    individuals.add(child1);
                    individuals.add(child2);

            }else{
                i+=0;
            }
        }

        //5. remove some individuals
        Collections.sort(individuals);
        for(int i=0;i<childsNum;i++){
            int lastIndex = individuals.size()-1;
            individuals.remove(lastIndex);
        }

    }

    public Individual getBest (){
        Collections.sort(individuals);
        //current best
        return individuals.get(0);
    }
}
