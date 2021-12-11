package org.dcu.student.sem1.ca642.asymmetric.signatures;

public class FakeHashFunction implements HashFunction {


    @Override
    public Integer apply(final Integer integer) {
        return integer;
    }
}
