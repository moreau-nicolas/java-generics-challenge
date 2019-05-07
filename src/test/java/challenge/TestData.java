package challenge;

import domain.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@SuppressWarnings("unused") // Used through the Compiler API.
class TestData {

    static final List<LivingBeing> LIVING_BEING_LIST = emptyList();
    static final List<Animal> ANIMAL_LIST = emptyList();
    static final List<Cat> CAT_LIST = emptyList();
    static final List<Dog> DOG_LIST = emptyList();
    static final List<Plant> PLANT_LIST = emptyList();
    static final List<Zebra> ZEBRA_LIST = emptyList();

    static final Set<Animal> ANIMAL_SET = emptySet();
    static final Set<Cat> CAT_SET = emptySet();

    static final Comparator<LivingBeing> COMPARE_LIVING_BEINGS = (a, b) -> 0;
    static final Comparator<Animal> COMPARE_ANIMALS = (a, b) -> 0;
    static final Comparator<Cat> COMPARE_CATS = (a, b) -> 0;
    static final Comparator<Dog> COMPARE_DOGS = (a, b) -> 0;
}
