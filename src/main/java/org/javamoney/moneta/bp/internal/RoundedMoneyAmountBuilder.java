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

import org.javamoney.moneta.bp.RoundedMoney;
import org.javamoney.moneta.bp.spi.AbstractAmountBuilder;

import org.javamoney.bp.api.CurrencyUnit;
import org.javamoney.bp.api.MonetaryContext;
import org.javamoney.bp.api.MonetaryContextBuilder;
import org.javamoney.bp.api.NumberValue;
import java.math.RoundingMode;

/**
 * Implementation of {@link org.javamoney.bp.api.MonetaryAmountFactory} creating instances of {@link org.javamoney.moneta
 * .RoundedMoney}.
 *
 * @author Anatole Tresch
 */
public class RoundedMoneyAmountBuilder extends AbstractAmountBuilder<RoundedMoney> {

    static final MonetaryContext DEFAULT_CONTEXT =
            MonetaryContextBuilder.of(RoundedMoney.class).setPrecision(0).set(RoundingMode.HALF_EVEN).build();
    static final MonetaryContext MAX_CONTEXT =
            MonetaryContextBuilder.of(RoundedMoney.class).setPrecision(0).set(RoundingMode.HALF_EVEN).build();

    /*
     * (non-Javadoc)
     * @see org.javamoney.moneta.bp.spi.AbstractAmountFactory#of(org.javamoney.bp.api.CurrencyUnit,
     * java.lang.Number, org.javamoney.bp.api.MonetaryContext)
     */
    @Override
    protected RoundedMoney create(Number number, CurrencyUnit currency, MonetaryContext monetaryContext) {
        return RoundedMoney.of(number, currency, monetaryContext);
    }

    @Override
    public NumberValue getMaxNumber() {
        return null;
    }

    @Override
    public NumberValue getMinNumber() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.javamoney.bp.api.MonetaryAmountFactory#getAmountType()
     */
    @Override
    public Class<RoundedMoney> getAmountType() {
        return RoundedMoney.class;
    }

    /*
     * (non-Javadoc)
     * @see org.javamoney.moneta.bp.spi.AbstractAmountFactory#loadDefaultMonetaryContext()
     */
    @Override
    protected MonetaryContext loadDefaultMonetaryContext() {
        return DEFAULT_CONTEXT;
    }

    /*
     * (non-Javadoc)
     * @see org.javamoney.moneta.bp.spi.AbstractAmountFactory#loadMaxMonetaryContext()
     */
    @Override
    protected MonetaryContext loadMaxMonetaryContext() {
        return MAX_CONTEXT;
    }

}
