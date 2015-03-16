/**
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.moneta.bp.internal;

import org.javamoney.bp.api.CurrencyUnit;
import org.javamoney.bp.api.MonetaryRounding;
import org.javamoney.bp.api.MonetaryRoundings;
import org.javamoney.bp.api.RoundingQuery;
import org.javamoney.bp.api.spi.RoundingProviderSpi;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * Defaulr implementation of a {@link org.javamoney.bp.api.spi.RoundingProviderSpi} that creates instances of {@link org
 * .javamoney.moneta.format.DefaultRounding} that relies on the default fraction units defined by {@link java.util
 * .Currency#getDefaultFractionDigits()}.
 */
public class DefaultRoundingProvider implements RoundingProviderSpi {

    public static final String DEFAULT_ROUNDING_ID = "default";
    private Set<String> roundingsIds = new HashSet<>();

    public DefaultRoundingProvider() {
        roundingsIds.add(DEFAULT_ROUNDING_ID);
        roundingsIds = Collections.unmodifiableSet(roundingsIds);
    }

    @Override
    public String getProviderName() {
        return "default";
    }

    /**
     * Evaluate the rounding that match the given query.
     *
     * @return the (shared) default rounding instances matching, never null.
     */
    public MonetaryRounding getRounding(RoundingQuery roundingQuery) {
        if (roundingQuery.get(GregorianCalendar.class) != null || roundingQuery.get(Calendar.class) != null) {
            return null;
        }
        CurrencyUnit currency = roundingQuery.getCurrency();
        if (currency != null) {
            RoundingMode roundingMode = roundingQuery.get(RoundingMode.class);
            if (roundingMode == null) {
                roundingMode = RoundingMode.HALF_EVEN;
            }
            if (Boolean.TRUE.equals(roundingQuery.getBoolean("cashRounding"))) {
                if (currency.getCurrencyCode().equals("CHF")) {
                    return new DefaultCashRounding(currency, RoundingMode.HALF_UP, 5);
                } else {
                    return new DefaultCashRounding(currency, 1);
                }
            }
            return new DefaultRounding(currency, roundingMode);
        }
        Integer scale = roundingQuery.getScale();
        if (scale == null) {
            scale = 2;
        }
        MathContext mc = roundingQuery.get(MathContext.class);
        RoundingMode roundingMode = roundingQuery.get(RoundingMode.class);
        if (mc != null) {
            return new DefaultRounding(scale, mc.getRoundingMode());
        } else if (roundingMode != null) {
            return new DefaultRounding(scale, roundingMode);
        } else if (roundingQuery.getRoundingName() != null && DEFAULT_ROUNDING_ID.equals(roundingQuery.getRoundingName())) {
            return MonetaryRoundings.getDefaultRounding();
        }
        return null;
    }


    @Override
    public Set<String> getRoundingNames() {
        return roundingsIds;
    }

}