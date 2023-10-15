package com.pawith.commonmodule.utils;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.JavaTypeArbitraryGenerator;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.StringArbitrary;

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

    private static final FixtureMonkey JAVA_TYPE_BASED_FIXTURE_MONKEY = FixtureMonkey.builder()
        .javaTypeArbitraryGenerator(new JavaTypeArbitraryGenerator() {
            @Override
            public StringArbitrary strings() {
                return Arbitraries.strings().alpha().numeric().ofMinLength(1).ofMaxLength(10);
            }
        })
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

    public static FixtureMonkey getJavaTypeBasedFixtureMonkey() {
        return JAVA_TYPE_BASED_FIXTURE_MONKEY;
    }


}
