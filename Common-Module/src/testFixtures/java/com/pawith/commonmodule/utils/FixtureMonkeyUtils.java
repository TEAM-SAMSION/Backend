package com.pawith.commonmodule.utils;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

public class FixtureMonkeyUtils {
    private FixtureMonkeyUtils() {
    }

    private static final FixtureMonkey CONSTRUCT_BASED_FIXTURE_MONKEY = FixtureMonkey.builder()
        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
        .defaultNotNull(true)
        .build();

    private static final FixtureMonkey BUILDER_BASED_FIXTURE_MONKEY = FixtureMonkey.builder()
        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
        .defaultNotNull(true)
        .build();

    private static final FixtureMonkey REFLECTION_BASED_FIXTURE_MONKEY = FixtureMonkey.builder()
        .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
        .defaultNotNull(true)
        .build();

    public static FixtureMonkey getConstructBasedFixtureMonkey() {
        return CONSTRUCT_BASED_FIXTURE_MONKEY;
    }

    public static FixtureMonkey getBuilderBasedFixtureMonkey() {
        return BUILDER_BASED_FIXTURE_MONKEY;
    }

    public static FixtureMonkey getReflectionbasedFixtureMonkey() {
        return REFLECTION_BASED_FIXTURE_MONKEY;
    }
}
