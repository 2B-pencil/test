//Population class
class Population {

    int popSize = 10;
    Individual[] individuals = new Individual[10];
    int fittest = 0;

    //Initialize population
    public void initializePopulation(int size) 
    {
        for (int i = 0; i < individuals.length; i++) 
        {
            individuals[i] = new Individual();
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        int maxFit = 0;
        fittest = individuals[0].fitness;
        for (int i = 1; i < individuals.length; i++) 
        {
            if (fittest <= individuals[i].fitness) 
            {
                maxFit = i;
                fittest = individuals[i].fitness;
            }
        }
        //fittest = individuals[maxFit].fitness;
        return individuals[maxFit];
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() 
    {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) 
        {
            if (individuals[i].fitness > individuals[maxFit1].fitness) 
            {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } 
            else if (individuals[i].fitness > individuals[maxFit2].fitness) 
            {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() 
    {
        int minFit = 0;
        int fitness = 5;
        for (int i = 0; i < individuals.length; i++) 
        {
            if (fitness >= individuals[i].fitness) 
            {
                minFit = i;
                fitness = individuals[i].fitness;
            }
        }
        return minFit;
    }

    //Calculate fitness of each individual
    public void calculateFitness() 
    {

        for (int i = 0; i < individuals.length; i++) 
        {
            individuals[i].calcFitness();
        }
        getFittest();
    }

}