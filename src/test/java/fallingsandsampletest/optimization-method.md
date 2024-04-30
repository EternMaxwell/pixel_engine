# Method

### Subjects

2 parts - water simulation and optimization.

- For water simulation, we have different implementations of simulating water spreading direction - random or change if blocked.
- For optimization, we have different implementations of grid - step all elements, 
chunks with rectangle, sub-chunks with sleep flag, quadtree, and sub-chunks with rectangle.

2 subjects were tested for the water simulation experiment, e.g. 2 different liquid implementations was used.

- Subject 1: Randomized water spread dir.
- Subject 2: Water will have a random dir when it is created, but only change if blocked.

5 subjects were tested for the optimization experiment, e.g. 5 different grid implementations was used.

- Subject 1: Step all the elements from the bottom to the top. 
Also, the step will be done from left to right for one tick and then from right to left for the next tick.
- Subject 2: Add chunks to the grid, and in each chunk, it will contain a rectangle indicating the area have to be updated. 
The rectangle will also be updated through time.
- Subject 3: Add chunks to the grid, and each chunk itself will be divided into sub-chunks, each of them has an awake flag to indicate whether it needs to be updated.
- Subject 4: Add chunks to the grid, and the chunk itself will be the root of a quadtree, 
in which each node will have an awake flag to indicate whether it needs to be updated.
- Subject 5: Add chunks and sub-chunks, and each sub-chunk will have a rectangle indicating the area have to be updated.

And for some of the subjects, the chunk size and sub chunk size or amount can be altered.

3 subjects were tested for the multithreading experiment, e.g. 3 different multithreading methods was used.

- Subject 1: Use a single thread to update the grid.
- Subject 2: Use multiple threads to update the grid, each thread will update a chunk.
- Subject 3: Use multiple threads to update the grid, each thread is assigned a min x and max x value to update.

### Data Collection & Procedure

For the water simulation experiment, we are going to use the first grid implementation. 
Then, create a wall below to block water, create a square of water on top of it. We will collect the height of highest water block over time, until the water spreads out.

For the optimization experiment, we are going to use all 5 grid implementations, each being applied to different method of multithreading. 
Then, we will create a grid with a size of 1024x1024, and fill it with the similar setup with the water simulation experiment,
but this time we only track the time it takes to update the grid over time. The ticks to cover is TBD. And we will also test on other setups.

### Principle

For each experiment, we will use the same setup, and only change the subject of the experiment.
Each experiment will be run for a number of ticks.
