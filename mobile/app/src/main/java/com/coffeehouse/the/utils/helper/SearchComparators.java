package com.coffeehouse.the.utils.helper;

import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.models.CustomUser;

import java.util.Comparator;
import java.util.function.Function;

public class SearchComparators {
    public static final Comparator<AdminCustomUser> USER_COMPARATOR =
            Comparator.comparing((Function<AdminCustomUser, String>) CustomUser::getName).thenComparing((AdminCustomUser::getEmail));
}
