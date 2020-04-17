import java.util.Random;

/**
 *
 * @author Dao
 * @since 20200328
 * @version 1.0
 */

//Main class
public class SimpleDemoGA {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;

    public static void main(String[] args) 
    {

        Random rn = new Random();
        
        SimpleDemoGA demo = new SimpleDemoGA();
        
        //Initialize population
        demo.population.initializePopulation(10);
        
        //Calculate fitness of each individual
        demo.population.calculateFitness();
        
        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

        //While population gets an individual with maximum fitness
        for (;demo.population.fittest < 5;) 
        {
            ++demo.generationCount;
            
            //Do selection
            demo.selection();
            /*
            目前是按循序进行选择的
            */

            
            //Do crossover
            demo.crossover();
            
            //Do mutation under a random probability
            //5%的变异率——是整个种群按照5%还是个体按照5%进行变异
            //修改以前只对适应度最高的两个个体进行变异以及对变异概率进行修改
            for(int i = 0;i<demo.population.popSize;i++)
            {
                if (rn.nextInt(100) < 5) {
                    demo.mutation(i);
                }
            }

            //Add fittest offspring to population
            demo.addFittestOffspring();//确保上一代适应度最高的个体基因被保留
            
            //Calculate new fitness value 
            demo.population.calculateFitness();//重新计算最高适应度
            
            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
            if(demo.generationCount>5000)
            {
                System.out.println("No Solution After Searching 5000 Generaations ,Please Re-Search");
                return;
            }
        }

        System.out.println("\nSolution found in generation " + demo.generationCount);
        System.out.println("Fitness: "+demo.population.getFittest().fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(demo.population.getFittest().genes[i]);
        }

        System.out.println("");

    }

    //Selection选择
    //可更改为适应度最高的个体直接保存，其他个体采用轮盘赌形式选择是否保留
    void selection() 
    {
        
        //Select the most fittest individual
        fittest = population.getFittest();
        
        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    /*
     * 目前为只对适应度最高的个体进行交叉，这样很不明智，会是种群有50%的概率降低整个种群的最佳适应度。
     * Crossover交叉
    */
    void crossover() 
    {
        Random rn = new Random();
        
        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) 
        {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation变异
    void mutation(int i) 
    {
        Random rn = new Random();
        
        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[i].geneLength);

        //Flip values at the mutation point
        if (population.individuals[i].genes[mutationPoint] == 0)
        {
            population.individuals[i].genes[mutationPoint] = 1;
        }
        else 
        {
            population.individuals[i].genes[mutationPoint] = 0;
        }

        /*
        mutationPoint = rn.nextInt(population.individuals[0].geneLength);
        if (secondFittest.genes[mutationPoint] == 0) 
        {
            secondFittest.genes[mutationPoint] = 1;
        } 
        else 
        {
            secondFittest.genes[mutationPoint] = 0;
        }
        */

    }

    //Get fittest offspring
    Individual getFittestOffspring() 
    {
        if (fittest.fitness > secondFittest.fitness) 
        {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() 
    {
        
        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();
        
        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();
        
        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }

}

//Individual class
/*
class Individual {

    int fitness = 0;
    int[] genes = new int[5];
    int geneLength = 5;

    public Individual() {
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = rn.nextInt() % 2;
        }

        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        for (int i = 0; i < 5; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }

}
*/


//Population class
/*
class Population {

    int popSize = 10;
    Individual[] individuals = new Individual[10];
    int fittest = 0;

    //Initialize population
    public void initializePopulation(int size) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = i;
            }
        }
        fittest = individuals[maxFit].fitness;
        return individuals[maxFit];
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        int minFit = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFit >= individuals[i].fitness) {
                minFit = i;
            }
        }
        return minFit;
    }

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }

}
*/