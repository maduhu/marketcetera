package org.marketcetera.options;

import static org.marketcetera.trade.OptionType.*;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Test;
import org.marketcetera.module.ExpectedFailure;
import org.marketcetera.trade.Option;
import org.marketcetera.trade.OptionType;

public class OptionUtilsTest extends TestCase {
	public void testGetUSEquityOptionExpiration() throws Exception {
		assertExpiration(20, OptionUtils.getUSEquityOptionExpiration(Calendar.JANUARY, 2007));
		assertExpiration(17, OptionUtils.getUSEquityOptionExpiration(Calendar.FEBRUARY, 2007));
		assertExpiration(17, OptionUtils.getUSEquityOptionExpiration(Calendar.MARCH, 2007));
		assertExpiration(21, OptionUtils.getUSEquityOptionExpiration(Calendar.APRIL, 2007));
		assertExpiration(19, OptionUtils.getUSEquityOptionExpiration(Calendar.MAY, 2007));
		assertExpiration(16, OptionUtils.getUSEquityOptionExpiration(Calendar.JUNE, 2007));
		assertExpiration(21, OptionUtils.getUSEquityOptionExpiration(Calendar.JULY, 2007));
		assertExpiration(18, OptionUtils.getUSEquityOptionExpiration(Calendar.AUGUST, 2007));
		assertExpiration(22, OptionUtils.getUSEquityOptionExpiration(Calendar.SEPTEMBER, 2007));
		assertExpiration(20, OptionUtils.getUSEquityOptionExpiration(Calendar.OCTOBER, 2007));
		assertExpiration(17, OptionUtils.getUSEquityOptionExpiration(Calendar.NOVEMBER, 2007));
		assertExpiration(22, OptionUtils.getUSEquityOptionExpiration(Calendar.DECEMBER, 2007));
		assertExpiration(19, OptionUtils.getUSEquityOptionExpiration(Calendar.JANUARY, 2008));
		assertExpiration(16, OptionUtils.getUSEquityOptionExpiration(Calendar.FEBRUARY, 2008));
		assertExpiration(22, OptionUtils.getUSEquityOptionExpiration(Calendar.MARCH, 2008));
		assertExpiration(19, OptionUtils.getUSEquityOptionExpiration(Calendar.APRIL, 2008));
		assertExpiration(17, OptionUtils.getUSEquityOptionExpiration(Calendar.MAY, 2008));
		assertExpiration(21, OptionUtils.getUSEquityOptionExpiration(Calendar.JUNE, 2008));
		assertExpiration(19, OptionUtils.getUSEquityOptionExpiration(Calendar.JULY, 2008));
		assertExpiration(16, OptionUtils.getUSEquityOptionExpiration(Calendar.AUGUST, 2008));
		assertExpiration(20, OptionUtils.getUSEquityOptionExpiration(Calendar.SEPTEMBER, 2008));
		assertExpiration(18, OptionUtils.getUSEquityOptionExpiration(Calendar.OCTOBER, 2008));
		assertExpiration(22, OptionUtils.getUSEquityOptionExpiration(Calendar.NOVEMBER, 2008));
		assertExpiration(20, OptionUtils.getUSEquityOptionExpiration(Calendar.DECEMBER, 2008));
	}

	private void assertExpiration(int dayOfMonth, Date equityOptionExpiration) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(equityOptionExpiration);
		assertEquals(dayOfMonth, cal.get(Calendar.DAY_OF_MONTH));
	}
	
	public void testGetNextUSEquityOptionExpiration() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		Date date = OptionUtils.getNextUSEquityOptionExpiration();
//		assertTrue(currentTimeMillis < date.getTime());
	}
    /**
     * Tests {@link OptionUtils#getOsiOptionFromString(String)}.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void testOsiOptionFromString()
            throws Exception
    {
        new ExpectedFailure<NullPointerException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString(null);
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("");
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("this-is-not-a-valid-osi-option");
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("21-characters-not-osi");
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("MSFT  991122p12345123"); // valid except 'Put' indicator is lower-case
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("MSFT  991122c12345123"); // valid except 'Call' indicator is lower-case
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("MSFT  991122 12345123"); // option-type missing
            }
        };
        new ExpectedFailure<IllegalArgumentException>() {
            @Override
            protected void run()
                    throws Exception
            {
                OptionUtils.getOsiOptionFromString("MSFT  991122 12345123"); // option-type missing
            }
        };
        verifyOption(OptionUtils.getOsiOptionFromString("123456301122P12345123"),
                     "123456",
                     OptionType.Put,
                     "20301122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001122P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  000122P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21000122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  000922P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21000922",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001022P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001022",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001222P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001222",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001201P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001201",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  201209P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "20201209",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  201210P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "20201210",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  201219P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "20201219",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001220P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001220",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("123456001220P12345123"),
                     "123456",
                     OptionType.Put,
                     "21001220",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  001229P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "21001229",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  201230P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "20201230",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("MSFT  501231P12345123"),
                     "MSFT",
                     OptionType.Put,
                     "20501231",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("x     301122P12345123"),
                     "x",
                     OptionType.Put,
                     "20301122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("xx    301122P12345123"),
                     "xx",
                     OptionType.Put,
                     "20301122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("ABCDEF301122C12345123"),
                     "ABCDEF",
                     OptionType.Call,
                     "20301122",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("ABCDEF000431C12345123"), // invalid date
                     "ABCDEF",
                     OptionType.Call,
                     "21000431",
                     new BigDecimal("12345.123"));
        verifyOption(OptionUtils.getOsiOptionFromString("ABCDEF301122C00000000"),
                     "ABCDEF",
                     OptionType.Call,
                     "20301122",
                     new BigDecimal("0.0"));
        verifyOption(OptionUtils.getOsiOptionFromString("ABCDEF301122C99999999"),
                     "ABCDEF",
                     OptionType.Call,
                     "20301122",
                     new BigDecimal("99999.999"));
        Locale currentLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.FRANCE);
            assertEquals(',',
                         DecimalFormatSymbols.getInstance().getDecimalSeparator());
            verifyOption(OptionUtils.getOsiOptionFromString("ABCDEF301122C12345123"),
                         "ABCDEF",
                         OptionType.Call,
                         "20301122",
                         new BigDecimal("12345.123"));
        } finally {
            Locale.setDefault(currentLocale);
        }
    }
    /**
     * Tests {@link OptionType#getInstanceForOSIValue(char)}.
     *
     * @throws Exception if an unexpected error occurs
     */
    @Test
    public void instanceForOSIValue()
            throws Exception
    {
        for(char badOptionType : new char[] { ' ', 'x', 'c', 'p' }) {
            assertEquals(Unknown,
                         OptionUtils.getOptionTypeForOSICharacter(badOptionType));
        }
        assertEquals(Put,
                     OptionUtils.getOptionTypeForOSICharacter('P'));
        assertEquals(Call,
                     OptionUtils.getOptionTypeForOSICharacter('C'));
    }
    /**
     * Verifies that the given <code>Option</code> matches the given expected attributes.
     *
     * @param inOption inOption an <code>Option</code> value
     * @param inExpectedSymbol inSymbol a <code>String</code> value
     * @param inExpectedType inOptionType an <code>OptionType</code> value
     * @param inExpectedExpiry inExpiry a <code>String</code> value
     * @param inExpectedStrike inStrike a <code>BigDecimal</code> value
     * @throws Exception if an unexpected error occurs
     */
    private static void verifyOption(Option inOption,
                                     String inExpectedSymbol,
                                     OptionType inExpectedType,
                                     String inExpectedExpiry,
                                     BigDecimal inExpectedStrike)
            throws Exception
    {
        assertNotNull(inOption);
        assertEquals(inExpectedSymbol,
                     inOption.getSymbol());
        assertEquals(inExpectedType,
                     inOption.getType());
        assertEquals(inExpectedExpiry,
                     inOption.getExpiry());
        assertEquals(inExpectedStrike,
                     inOption.getStrikePrice());
    }
}
