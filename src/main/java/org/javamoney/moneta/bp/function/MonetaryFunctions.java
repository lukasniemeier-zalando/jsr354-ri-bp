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
package org.javamoney.moneta.bp.function;

import java.util.Comparator;
import java.util.Objects;

import org.javamoney.bp.api.MonetaryAmount;
import org.javamoney.bp.api.MonetaryException;
import org.javamoney.bp.api.convert.CurrencyConversion;
import org.javamoney.bp.api.convert.ExchangeRate;
import org.javamoney.bp.api.convert.ExchangeRateProvider;

import org.javamoney.moneta.bp.spi.MoneyUtils;

/**
 * This singleton class provides access to the predefined monetary functions.
 *
 * @author otaviojava
 * @author anatole
 */
public final class MonetaryFunctions {
    private static final Comparator<MonetaryAmount> NUMBER_COMPARATOR = new Comparator<MonetaryAmount>() {
        @Override
        public int compare(MonetaryAmount o1, MonetaryAmount o2) {
            return o1.getNumber().compareTo(o2.getNumber());
        }
    };

    private static final Comparator<MonetaryAmount> CURRENCY_COMPARATOR = new Comparator<MonetaryAmount>() {
        @Override
        public int compare(MonetaryAmount o1, MonetaryAmount o2) {
            return o1.getCurrency().compareTo(o2.getCurrency());
        }
    };

    /**
     * Get a comparator for sorting CurrencyUnits ascending.
     *
     * @return the Comparator to sort by CurrencyUnit in ascending order, not null.
     */
    public static Comparator<MonetaryAmount> sortCurrencyUnit(){
        return CURRENCY_COMPARATOR;
    }

	/**
	 * comparator to sort the {@link MonetaryAmount} considering the
	 * {@link ExchangeRate}
	 * @param provider the rate provider to be used.
	 * @return the sort of {@link MonetaryAmount} using {@link ExchangeRate}
	 */
	public static Comparator<? super MonetaryAmount> sortValiable(
			final ExchangeRateProvider provider) {
        return new Comparator<MonetaryAmount>() {
            @Override
            public int compare(MonetaryAmount m1, MonetaryAmount m2) {
                CurrencyConversion conversor = provider.getCurrencyConversion(m1
                        .getCurrency());
                return m1.compareTo(conversor.apply(m2));
            }
        };
	}

	/**
	 * Descending order of
	 * {@link MonetaryFunctions#sortValiable(ExchangeRateProvider)}
	 * @param provider the rate provider to be used.
	 * @return the Descending order of
	 *         {@link MonetaryFunctions#sortValiable(ExchangeRateProvider)}
	 */
	public static Comparator<? super MonetaryAmount> sortValiableDesc(
			final ExchangeRateProvider provider) {
		return new Comparator<MonetaryAmount>() {
            @Override
            public int compare(MonetaryAmount o1, MonetaryAmount o2) {
                return sortValiable(provider).compare(o1, o2) * -1;
            }
        };
	}

    /**
     * Get a comparator for sorting CurrencyUnits descending.
     * @return the Comparator to sort by CurrencyUnit in descending order, not null.
     */
    public static Comparator<MonetaryAmount> sortCurrencyUnitDesc(){
        return new Comparator<MonetaryAmount>() {
            @Override
            public int compare(MonetaryAmount o1, MonetaryAmount o2) {
                return sortCurrencyUnit().compare(o1, o2) * -1;
            }
        };
    }

    /**
     * Get a comparator for sorting amount by number value ascending.
     * @return the Comparator to sort by number in ascending way, not null.
     */
    public static Comparator<MonetaryAmount> sortNumber(){
        return NUMBER_COMPARATOR;
    }

    /**
     * Get a comparator for sorting amount by number value descending.
     * @return the Comparator to sort by number in descending way, not null.
     */
    public static Comparator<MonetaryAmount> sortNumberDesc(){
        return new Comparator<MonetaryAmount>() {
            @Override
            public int compare(MonetaryAmount o1, MonetaryAmount o2) {
                return sortNumber().compare(o1, o2) * -1;
            }
        };
    }

//    /**
//	 * Create predicate that filters by CurrencyUnit.
//	 * @param currencies
//	 *            the target {@link org.javamoney.bp.api.CurrencyUnit}
//	 * @return the predicate from CurrencyUnit
//	 */
//	public static Predicate<MonetaryAmount> isCurrency(
//			CurrencyUnit... currencies) {
//
//		if (Objects.isNull(currencies) || currencies.length == 0) {
//			return m -> true;
//		}
//		Predicate<MonetaryAmount> predicate = null;
//
//		for (CurrencyUnit currencyUnit : currencies) {
//			if (Objects.isNull(predicate)) {
//				predicate = m -> m.getCurrency().equals(currencyUnit);
//			} else {
//				predicate = predicate.or(m -> m.getCurrency().equals(
//						currencyUnit));
//			}
//		}
//		return predicate;
//    }
//
//    /**
//     * Create predicate that filters by CurrencyUnit.
//     * @param currencyUnit the target {@link org.javamoney.bp.api.CurrencyUnit}
//     * @return the predicate from CurrencyUnit
//     */
//	public static Predicate<MonetaryAmount> filterByExcludingCurrency(
//			CurrencyUnit... currencies) {
//
//		if (Objects.isNull(currencies) || currencies.length == 0) {
//			return m -> true;
//		}
//		return isCurrency(currencies).negate();
//    }
//
//    /**
//     * Creates filter using isGreaterThan in MonetaryAmount.
//     * @param amount
//     * @return the filter with isGreaterThan conditions
//     */
//    public static Predicate<MonetaryAmount> isGreaterThan(MonetaryAmount amount){
//        return m -> m.isGreaterThan(amount);
//    }
//
//    /**
//     * Creates filter using isGreaterThanOrEqualTo in MonetaryAmount
//     * @param amount
//     * @return the filter with isGreaterThanOrEqualTo conditions
//     */
//    public static Predicate<MonetaryAmount> isGreaterThanOrEqualTo(MonetaryAmount amount){
//        return m -> m.isGreaterThanOrEqualTo(amount);
//    }
//
//    /**
//     * Creates filter using isLessThan in MonetaryAmount
//     * @param amount
//     * @return the filter with isLessThan conditions
//     */
//    public static Predicate<MonetaryAmount> isLessThan(MonetaryAmount amount){
//        return m -> m.isLessThan(amount);
//    }
//
//    /**
//     * Creates filter using isLessThanOrEqualTo in MonetaryAmount
//     * @param amount
//     * @return the filter with isLessThanOrEqualTo conditions
//     */
//    public static Predicate<MonetaryAmount> isLessThanOrEqualTo(MonetaryAmount amount){
//        return m -> m.isLessThanOrEqualTo(amount);
//    }
//
//    /**
//     * Creates a filter using the isBetween predicate.
//     * @param min min value inclusive, not null.
//     * @param max max value inclusive, not null.
//     * @return the Predicate between min and max.
//     */
//    public static Predicate<MonetaryAmount> isBetween(MonetaryAmount min, MonetaryAmount max){
//        return isLessThanOrEqualTo(max).and(isGreaterThanOrEqualTo(min));
//    }

    /**
     * Adds two monetary together
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @throws NullPointerException if a o b be null
     * @throws MonetaryException    if a and b have different currency
     */
    public static MonetaryAmount sum(MonetaryAmount a, MonetaryAmount b){
        MoneyUtils.checkAmountParameter(Objects.requireNonNull(a), Objects.requireNonNull(b.getCurrency()));
        return a.add(b);
    }

    /**
     * Returns the smaller of two {@code MonetaryAmount} values. If the arguments
     * have the same value, the result is that same value.
     * @param a an argument.
     * @param b another argument.
     * @return the smaller of {@code a} and {@code b}.
     */
	static MonetaryAmount min(MonetaryAmount a, MonetaryAmount b) {
        MoneyUtils.checkAmountParameter(Objects.requireNonNull(a), Objects.requireNonNull(b.getCurrency()));
        return a.isLessThan(b) ? a : b;
    }

    /**
     * Returns the greater of two {@code MonetaryAmount} values. If the
     * arguments have the same value, the result is that same value.
     * @param a an argument.
     * @param b another argument.
     * @return the larger of {@code a} and {@code b}.
     */
	static MonetaryAmount max(MonetaryAmount a, MonetaryAmount b) {
        MoneyUtils.checkAmountParameter(Objects.requireNonNull(a), Objects.requireNonNull(b.getCurrency()));
        return a.isGreaterThan(b) ? a : b;
    }

//    /**
//     * Creates a BinaryOperator to sum.
//     * @return the sum BinaryOperator, not null.
//     */
//    public static BinaryOperator<MonetaryAmount> sum(){
//        return MonetaryFunctions::sum;
//    }
//
//	/**
//	 * return the sum and convert all values to specific currency using the
//	 * provider, if necessary
//	 * @param provider
//	 * @param currency
//	 *            currency
//	 * @return the list convert to specific currency unit
//	 */
//	public static BinaryOperator<MonetaryAmount> sum(
//			ExchangeRateProvider provider, CurrencyUnit currency) {
//		CurrencyConversion currencyConversion = provider
//				.getCurrencyConversion(currency);
//
//		return (m1, m2) -> currencyConversion.apply(m1).add(
//				currencyConversion.apply(m2));
//	}
//
//    /**
//	 * Creates a BinaryOperator to calculate the minimum amount
//	 * @return the minimum BinaryOperator, not null.
//	 */
//    public static BinaryOperator<MonetaryAmount> min(){
//        return MonetaryFunctions::min;
//    }
//
//	/**
//	 * return the minimum value, if the monetary amounts have different
//	 * currencies, will converter first using the given ExchangeRateProvider
//	 * @param provider
//	 *            the ExchangeRateProvider to convert the currencies
//	 * @return the minimum value
//	 */
//	public static BinaryOperator<MonetaryAmount> min(
//			ExchangeRateProvider provider) {
//
//		return (m1, m2) -> {
//			CurrencyConversion conversion = provider.getCurrencyConversion(m1
//					.getCurrency());
//
//			if (m1.isGreaterThan(conversion.apply(m2))) {
//				return m2;
//			}
//			return m1;
//		};
//	}
//
//    /**
//	 * Creates a BinaryOperator to calculate the maximum amount.
//	 * @return the max BinaryOperator, not null.
//	 */
//    public static BinaryOperator<MonetaryAmount> max(){
//        return MonetaryFunctions::max;
//    }
//
//	/**
//	 * return the maximum value, if the monetary amounts have different
//	 * currencies, will converter first using the given ExchangeRateProvider
//	 * @param provider
//	 *            the ExchangeRateProvider to convert the currencies
//	 * @return the maximum value
//	 */
//	public static BinaryOperator<MonetaryAmount> max(
//			ExchangeRateProvider provider) {
//
//		return (m1, m2) -> {
//			CurrencyConversion conversion = provider
//					.getCurrencyConversion(m1.getCurrency());
//
//			if (m1.isGreaterThan(conversion.apply(m2))) {
//				return m1;
//			}
//			return m2;
//		};
//	}


}