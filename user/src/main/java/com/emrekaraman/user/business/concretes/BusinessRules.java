package com.emrekaraman.user.business.concretes;

import com.emrekaraman.user.core.utilities.Result;

public class BusinessRules {
    public static Result run(Result... logics) {
        for (Result logic : logics) {
            if (!logic.isSuccess()) {
                return logic;
            }
        }
        return null;
    }
}
