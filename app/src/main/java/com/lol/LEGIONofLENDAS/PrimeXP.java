package com.lol.LEGIONofLENDAS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PrimeXP {
    private int level;
    private static ArrayList<Integer> lookuplevels = new ArrayList<>(Collections.singletonList(2));
    public PrimeXP(int currentLevel){
        this.level = currentLevel;
    }
    private int nextPrime(int M) {
        if(!isPrime(M)) M = nextPrime(++M);
        return M;
    }
    private boolean isPrime(int M) {
        for(int i = 2; i <= Math.sqrt(M); i++)
            if(M % i == 0) return false;
        return true;
    }
    public int getNextXP(){
        int next = nextPrime(lookuplevels.get(level));
        if(!lookuplevels.contains(lookuplevels.get(level)))
            lookuplevels.add(next);
        return next;
    }
}