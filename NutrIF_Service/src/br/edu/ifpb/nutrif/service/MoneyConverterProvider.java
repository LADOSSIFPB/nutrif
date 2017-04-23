package br.edu.ifpb.nutrif.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class MoneyConverterProvider implements ParamConverterProvider {

	private final MoneyConverter converter = new MoneyConverter();

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (!rawType.equals(Money.class))
			return null;
		return (ParamConverter<T>) converter;
	}

	public class MoneyConverter implements ParamConverter<Money> {

		public Money fromString(String value) {
			if (value == null || value.isEmpty())
				return null; // change this for production

			CurrencyUnit real = CurrencyUnit.of("BRL");
			return Money.of(real, Double.parseDouble(value));
		}

		public String toString(Money value) {
			if (value == null)
				return "";
			return value.getAmount().toString(); // change this for production
		}
	}
}