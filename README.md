
[![](https://jitpack.io/v/jockbert/particle_sim_config.svg)](https://jitpack.io/#jockbert/particle_sim_config)

This project is both a particle simulation data exchange format definition and a reference library implementation.

# How to use the library

Add the following to your _gradle.build_ file:

```
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
	compile 'com.github.jockbert:particle_sim_config:+'
}
```


# The data format definition

```
" Simulation area size (ax,ay) "
ax ay

" A series of pc number of particles with
position (px,py) and velocity (vx, vy) "
pc
px py vx vy

" Expected or calculated simulation wall momentum value m at time t "
rc
t m 
```

Variable name suffix ___x___ and ___y___ denotes double floating point numbers describing ___x___- and ___y___-components of a cartesian vector. Floating point numbers are formated with United States locale. Variable name suffix ___c___ denotes an integer format enumeration counter of some sort.

The data format starts with simulation area width and height are described by the doubles _0 &lt; __ax__, __ay__ &leq; 1e6_. 

Next comes a integer _0 &leq; __pc__ &leq; 1e5_, denoting the number of particles in the simulation. Following pc comes a series of _4*__pc___ number of values describing the each particle state at time _0_. The values __px__, __py__, __vx__ and __vy__ denotes the position and velocity of each particle.

All particles are assumed to have mass 1 unit and radius 1. No overlapp is allowed between particles and all particles are always completely within the simulation area.

The particle initial position is relative one of the corners of the simulation area so that _1 &leq; __px__ &leq; (__ax__ -1)_ and _1 &leq; __py__ &leq; (__ay__ -1)_. Particles initial velocity has the restriction _-1e4 &leq; __vx__, __vy__ &leq; 1e4_

After particle data, an integer _0 &leq; __rc__ &leq; 1e_ denotes a number of expected or in sumulation calculated momentum results ___m___ transfered from particles to simulation area walls at a given  time _t_. Both _m_ and _t_ are doubles _&geq; 0_.

If a collision occurs at time ___t___, the momentum is defined to have been transfered and velocities have changed at that given time.

Everything in between two quotation marks (_"_) is regarded as comment. All numeric values must be separated by some whitespace and/or comment.

# Some simulation configuration examples
The examples can also be found in folder [src/main/resources/](src/main/resources/)

_File 1:_
```
" One particle with horizontal velocity "
4 4 1
2 2 1 0

3
0 0
4 4
5 6
```

_File 2:_
```
" 5 touching particles with vertical velocity "
10 4 5
1 2 0 4
3 2 0 4
5 2 0 4
7 2 0 -4
9 2 0 -4

2
1 20
2 40
```

_File 3:_
```
" diagonal paricles with both
corner collisions and particle
collisions "

6 4 2
1 1 1 1
6 3 -1 -1

6
0 0
1 0
3 8
5 16
7.99 24
8.01 32
```


