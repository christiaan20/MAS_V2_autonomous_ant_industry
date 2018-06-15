package Model_pk.Testing_Classes;

/**
 * Created by christiaan on 15/06/18.
 */
public class Parameter_Struct
{
    parameter_setting pheromone_dist;
    parameter_setting starting_workers;
    pheromone_enum pheromone_setting;
    parameter_setting scenario;
    task_distribution_enum task_distribution;


    public Parameter_Struct(parameter_setting pheromone_dist, parameter_setting starting_workers, pheromone_enum pheromone_setting, parameter_setting scenario, task_distribution_enum task_distribution) {
        this.pheromone_dist = pheromone_dist;
        this.starting_workers = starting_workers;
        this.pheromone_setting = pheromone_setting;
        this.scenario = scenario;
        this.task_distribution = task_distribution;
    }

    public parameter_setting getPheromone_dist() {
        return pheromone_dist;
    }

    public void setPheromone_dist(parameter_setting pheromone_dist) {
        this.pheromone_dist = pheromone_dist;
    }

    public parameter_setting getStarting_workers() {
        return starting_workers;
    }

    public void setStarting_workers(parameter_setting starting_workers) {
        this.starting_workers = starting_workers;
    }

    public pheromone_enum getPheromone_setting() {
        return pheromone_setting;
    }

    public void setPheromone_setting(pheromone_enum pheromone_setting) {
        this.pheromone_setting = pheromone_setting;
    }

    public parameter_setting getScenario() {
        return scenario;
    }

    public void setScenario(parameter_setting scenario) {
        this.scenario = scenario;
    }

    public task_distribution_enum getTask_distribution() {
        return task_distribution;
    }

    public void setTask_distribution(task_distribution_enum task_distribution) {
        this.task_distribution = task_distribution;
    }
}
