package com.pawith.tododomain.utils;

import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Register;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterTestFixtureEntityUtils {

    public static Register getRegisterEntity(){
        return FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .sample();
    }

    public static Register getRegisterEntityWithUnregistered(){
        return FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", false)
            .sample();
    }

    public static List<Register> getRegisterEntityList(int size){
        return FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .sampleList(size);
    }

    public static List<Register> getRegisterEntityListWithUnregistered(int size){
        return FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", false)
            .sampleList(size);
    }


}
