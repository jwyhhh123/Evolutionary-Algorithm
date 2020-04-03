
import java.util.ArrayList;
import java.util.Random;

/**
 * Wenyue Jin, 1966926
 */

public class Individual implements Comparable<Individual>{

    public char[] values;
    public int length;
    public String genes;
    public double fitness;

    protected Random random = new Random(System.currentTimeMillis());

    public Individual(int Chromosomelength){
        length = Chromosomelength;
        genes = "";

    }

    @Override
    public int compareTo(Individual o){
        Double fitnessMe = getFitness();
        Double fitnessOther = o.getFitness();
        //compare current getFitness to the next getFitness.
        //By descending.
        return -fitnessMe.compareTo(fitnessOther);
    }

    public void mutate(){

        int pos = random.nextInt(length);

        int step = random.nextBoolean()?1:-1;
        int index = indexOf(genes.charAt(pos));
        index+=step;

        //to be a ring, for example: {'0','1'} ------>  010101010101
        if(index<0)index = values.length-1;
        if(index>values.length-1)index = 0;


        String newGenes = "";
        for(int i = 0;i<genes.length();i++){
            if(i==pos){
                newGenes+=values[index];
            }else{
                newGenes+= genes.charAt(i);
            }
        }
        genes = newGenes;
    }

    public int indexOf(char genesChar){
        for(int i=0; i<values.length; i++){
            if(genesChar == values[i]){
                return i;
            }
        }
        return -1;
    }

    public Individual crossover(Individual object){
        //different individuals have different crossover objects.
        return null;
    }

    public double getFitness(){
        return fitness;
    }

    public String toString(){
        return genes;
    }

}

//BinaryMaximiser
class BinaryIndividual extends Individual{

    BinaryIndividual(int Chromosomelength){
        super(Chromosomelength);
        values = new char[]{'0','1'};

        for(int i = 0; i<length;i++){
            int index = random.nextInt(values.length);
            this.genes += values[index];
        }
    }

    @Override
    public Individual crossover(Individual object) {
        Individual target = new BinaryIndividual(length);
        int pos = random.nextInt(length);
        target.genes = genes.substring(0,pos)+object.genes.substring(pos);
        return target;
    }

    @Override
    public double getFitness(){
        fitness = 0.0;
        for(int i = 0; i<length; i++){
            if(genes.charAt(i)=='1'){
                fitness+=1;
            }
        }

        return fitness;
    }
}

//Weasel
class WeaselIndividual extends Individual{
    String Chromosome;
    WeaselIndividual(String Chromosome){
        super(Chromosome.length());
        this.Chromosome = Chromosome;
        values = new char[56];

        // create an array containing space, 26 lower and space, 26 upper.
        int count=0;
        for(int i = 0; i < 26; i++){
            values[count++] = (char)('a'+i);
        }
        values[26]=(char)32;    //ascii space
        values[27]=(char)44;
        values[28]=(char)45;
        values[29]=(char)46;

        count=count+4;
        for(int i = 0; i < 26; i++){
            values[count++]= (char)('A'+i);
        }

        for(int i = 0; i<length;i++){
            int index = random.nextInt(values.length);
            this.genes += values[index];
        }
    }

    @Override
    public Individual crossover(Individual object) {
        Individual target = new WeaselIndividual(Chromosome);
        int pos = random.nextInt(length);
        target.genes = genes.substring(0,pos)+ object.genes.substring(pos);
        return target;
    }

    @Override
    public double getFitness(){
        //fitness is set to a high value. distances between actual values and random values are got rid from it.
        fitness = 10000.0;
        for(int i = 0; i<length; i++){
            double distance = Math.abs(genes.charAt(i)-Chromosome.charAt(i));
            fitness-=distance;
        }
        return fitness;
    }
}

//Maths
class MathIndividual extends Individual{
    int integer;

    MathIndividual(int integer, int Chromosomelength){
        super(Chromosomelength);
        this.integer = integer;

        values = new char[14];
        for(int i=0; i<10; i++){
            values[i]=(char)('0'+i);
        }
        values[10]='+';
        values[11]='-';
        values[12]='*';
        values[13]='/';

        for(int i = 0; i<length;i++){
            int index = random.nextInt(values.length);
            this.genes += values[index];
        }

    }

    @Override
    public void mutate(){
        int pos = random.nextInt(length);

        int step = random.nextBoolean()?1:-1;
        int index = indexOf(genes.charAt(pos));
        index+=step;

        if(index<0)index = values.length-1;
        if(index>values.length-1)index = 0;


        String newG = "";
        for(int i = 0;i<genes.length();i++){
            if(i==pos){
                newG+=values[index];
            }else{
                newG+= genes.charAt(i);
            }
        }
        genes = newG;
        correction();
    }

    @Override
    public Individual crossover(Individual object){
        MathIndividual target = new MathIndividual(integer,length);
        int pos = random.nextInt(length);
        target.genes = genes.substring(0,pos) + object.genes.substring(pos);
        target.correction();
        return target;
    }

    public int calculate(){                                                                                             // <---------
        int result = 0;
        ArrayList<String> digits = new ArrayList<String>();
        ArrayList<String> operators = new ArrayList<String>();
        /** operators.sort() by precedence
         * sorting operators?
         * multiply()
         * divide()
         * add()
         * minus()
         */
        return result;
    }

    public String correction(){
        if(!validCheck1()){
            //do some correction
        }

        if(!validCheck2()){
            //do something
        }
        return genes;
    }

    // to check if ++ -- ** occur
    public boolean validCheck1(){                                                                                        // <---------
        boolean is = true;
        for(int i = 0; i < length; i++){
            for(int j = i+1; j<length; j++){
                if(index(genes.charAt(i))>9 && index(genes.charAt(j))>9){
                    is = false;
                }
            }
        }

        return is;
    }

    // to check if +12*33+11- happens
    public boolean validCheck2(){
        boolean is = true;
        if(index(genes.charAt(0))>9){
            is = false;
        }
        if(index(genes.charAt(length-1))>9){
            is = false;
        }
        return is;
    }

        public int index(char ch){
        for(int i=0; i<values.length; i++){
            if(ch == values[i]){
                return i;
            }
        }
        return 10;
    }

    @Override
    public double getFitness(){
        fitness=10000.0;
        int difference = Math.abs(calculate()-integer);
        fitness-=difference;
        return fitness;
    }
}

