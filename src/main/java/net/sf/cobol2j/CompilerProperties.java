/**
 * 
 */
package net.sf.cobol2j;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Andrew
 *
 */
@XmlType(name = "CompilerProperties", propOrder = { "apostQuote", "codepage", "currency", "dbcs", "dispsign",
		"nsymbol" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CompilerProperties {
	private static final Charset IBM1047 = Charset.forName("CP1047");
	private static final String A = "A";
	@SuppressWarnings("unused")
	private static final String C = "C";
	@SuppressWarnings("unused")
	private static final String E = "E";
	private static final String S = "S";

	protected ApostQuote apostQuote;
	protected Arith arith;
	protected Charset codepage;
	/**
	 * You can use the CURRENCY option to provide an alternate default currency
	 * symbol to be used for a COBOL program. (The default currency symbol is the
	 * dollar sign ($).)
	 */
	protected String currency;
	/**
	 * Using DBCS causes the compiler to recognize X'0E' (SO) and X'0F' (SI) as
	 * shift codes for the double-byte portion of an alphanumeric literal.
	 */
	protected Dbcs dbcs;
	/**
	 * 
	 */
	protected DispSign dispSign;
	/**
	 * The NSYMBOL option controls the interpretation of the N symbol used in
	 * literals and PICTURE clauses, indicating whether national or DBCS processing
	 * is assumed. Permissible values are NAT | DBCS | NATIONAL.
	 */
	protected NSymbol nSymbol;
	
	public CompilerProperties() {
		this.apostQuote = CompilerProperties.ApostQuote.QUOTE;
		this.arith = CompilerProperties.Arith.COMPAT;
		this.codepage = IBM1047;
		this.currency = CurrencySymbols.US_DOLLAR_SIGN;
		this.dbcs = Dbcs.DBCS;
		this.dispSign = DispSign.COMPAT;
		this.nSymbol = NSymbol.DBCS;
	}
	
	@XmlElement(name = "apostQuote", required = false)
	public ApostQuote getApostQuote() {
		return this.apostQuote;
	}

	@XmlElement(name = "arith", required = false)
	public String getArith() {
		return this.getArithType().name();
	}

	public Arith getArithType() {
		return this.arith;
	}

	@XmlElement(name = "codepage", required = false)
	public Charset getCodepage() {
		return this.codepage;
	}

	@XmlElement(name = "currency", required = false)
	public String getCurrency() {
		return this.currency;
	}

	@XmlElement(name = "dbcs", required = false)
	public Dbcs getDbcs() {
		return this.dbcs;
	}

	@XmlElement(name = "dispsign")
	public DispSign getDispsign() {
		return this.dispSign;
	}

	@XmlElement(name = "nsymbol", required = false)
	public NSymbol getNSymbol() {
		return this.nSymbol;
	}

	@XmlElement(name = "apostQuote", required = false)
	public void setApostQuote(String apostQuote) {
		if (ApostQuote.APOST.name().equalsIgnoreCase(apostQuote) || A.equalsIgnoreCase(apostQuote)) {
			this.apostQuote = ApostQuote.APOST;
		} else {
			this.apostQuote = ApostQuote.QUOTE;
		}
	}

	@XmlElement(name = "arith", required = false)
	public void setArith(String arith) {
		this.arith = Arith.COMPAT.name().equalsIgnoreCase(arith) || Arith.EXTEND.name().equalsIgnoreCase(arith)
				? Arith.valueOf(arith)
				: Arith.COMPAT;
	}

	public void setCodepage(Charset codepage) {
		this.codepage = codepage;
	}

	@XmlElement(name = "codepage", required = false)
	public void setCodepage(String codepage) {
		this.codepage = Charset.isSupported(codepage) ? Charset.forName(codepage) : IBM1047;
	}

	@XmlElement(name = "currency", required = false)
	public void setCurrency(String currencyCode) {
		this.currency = CurrencySymbols.isValid(currencyCode) ? currencyCode : CurrencySymbols.US_DOLLAR_SIGN;
	}

	public void setDbcs(Dbcs dbcs) {
		this.dbcs = dbcs;
	}

	@XmlElement(name = "dbcs", required = false)
	public void setDbcs(String dbcs) {
		this.setDbcs(Dbcs.DBCS.name().equalsIgnoreCase(dbcs) || Dbcs.NODBCS.name().equalsIgnoreCase(dbcs)
				? Dbcs.valueOf(dbcs)
				: Dbcs.DBCS);
	}

	public void setDispSign(DispSign dispSign) {
		this.dispSign = dispSign;
	}

	@XmlElement(name = "dispsign")
	public void setDispSign(String dispSign) {
		if (S.equalsIgnoreCase(dispSign) || DispSign.SEP.name().equalsIgnoreCase(dispSign)) {
			this.dispSign = DispSign.SEP;
		} else {
			this.dispSign = DispSign.COMPAT;// any invalids will default to COMPAT, and valids will only be COMPAT
											// here
		}
	}

	public void setNSymbol(NSymbol nSymbol) {
		this.nSymbol = nSymbol;
	}

	@XmlElement(name = "nsymbol", required = false)
	public void setNSymbol(String nSymbol) {
		this.setNSymbol(
				NSymbol.NATIONAL.name().equalsIgnoreCase(nSymbol) || NSymbol.NAT.name().equalsIgnoreCase(nSymbol)
						|| NSymbol.DBCS.name().equalsIgnoreCase(nSymbol) ? NSymbol.valueOf(nSymbol) : NSymbol.NATIONAL);
	}

	public class Rules {

	}

	public enum ApostQuote {
		APOST, QUOTE;
	}

	public enum Arith {
		COMPAT, EXTEND;
	}

	public enum NSymbol {
		DBCS, NATIONAL, NAT;
	}

	public enum Dbcs {
		DBCS, NODBCS;
	}

	public enum DispSign {
		COMPAT, SEP;
	}

	static class CurrencySymbols {
		private static final String AUSTRAL_SIGN = "\u20B3";// "₳" 8371
		private static final String BITCOIN_SIGN = "\u20BF";// "₿" 8383
		private static final String CEDI_SIGN = "\u20B5";// "₵" 8373
		private static final String COLON_SIGN = "\u20A1"; // "₡" 8353
		private static final String CRUZEIRO_SIGN = "\u20A2";// "₢" 8354
		private static final String DONG_SIGN = "\u20AB";// "₫" 8363
		private static final String DRACHMA_SIGN = "\u20AF";// "₯" 8367
		private static final String EURO_CURRENCY_SIGN = "\u20A0";// "₠" 8352
		private static final String EURO_SIGN = "\u20AC";// "€" 8364
		private static final String FRENCH_FRANC_SIGN = "\u20A3";// "₣" 8355
		private static final String GERMAN_PENNY_SYMBOL = "\u20B0";// "₰" 8368
		private static final String GUARANI_SIGN = "\u20B2";// "₲" 8370
		private static final String HRYVNIA_SIGN = "\u20B4";// "₴" 8372
		private static final String INDIAN_RUPEE_SIGN = "\u20B9";// "₹" 8377
		private static final String KIP_SIGN = "\u20AD";// "₭" 8365
		private static final String LARI_SIGN = "\u20BE";// "₾" 8382
		private static final String LIRA_SIGN = "\u20A4";// "₤" 8356
		private static final String LIVRE_TOURNOIS_SIGN = "\u20B6";// "₶" 8374
		private static final String MANAT_SIGN = "\u20BC";// "₼" 8380
		private static final String MILL_SIGN = "\u20A5";// "₥" 8357
		private static final String NAIRA_SIGN = "\u20A6";// "₦" 8358
		private static final String NEW_SHEQEL_SIGN = "\u20AA";// "₪" 8362
		private static final String NORDIC_MARK_SIGN = "\u20BB";// "₻" 8379
		private static final String PESETA_SIGN = "\u20A7";// "₧" 8359
		private static final String PESO_SIGN = "\u20B1";// "₱" 8369
		private static final String RUBLE_SIGN = "\u20BD";// "₽" 8381
		private static final String RUPEE_SIGN = "\u20A8";// "₨" 8360
		private static final String SPESMILO_SIGN = "\u20B7";// "₷" 8375
		private static final String TENGE_SIGN = "\u20B8";// "₸" 8376
		private static final String TUGRIK_SIGN = "\u20AE";// "₮" 8366
		private static final String TURKISH_LIRA_SIGN = "\u20BA";// "₺" 8378
		private static final String US_DOLLAR_SIGN = "$";
		private static final String WON_SIGN = "\u20A9";// "₩" 8361
		private static final String[] CURRENCY_SYMBOLS = { US_DOLLAR_SIGN, EURO_CURRENCY_SIGN, COLON_SIGN,
				CRUZEIRO_SIGN, FRENCH_FRANC_SIGN, LIRA_SIGN, MILL_SIGN, NAIRA_SIGN, PESETA_SIGN, RUPEE_SIGN, WON_SIGN,
				NEW_SHEQEL_SIGN, DONG_SIGN, EURO_SIGN, KIP_SIGN, TUGRIK_SIGN, DRACHMA_SIGN, GERMAN_PENNY_SYMBOL,
				PESO_SIGN, GUARANI_SIGN, AUSTRAL_SIGN, HRYVNIA_SIGN, CEDI_SIGN, LIVRE_TOURNOIS_SIGN, SPESMILO_SIGN,
				TENGE_SIGN, INDIAN_RUPEE_SIGN, TURKISH_LIRA_SIGN, NORDIC_MARK_SIGN, MANAT_SIGN, RUBLE_SIGN, LARI_SIGN,
				BITCOIN_SIGN };

		public static final boolean isValid(String symbol) {
			return Arrays.binarySearch(CURRENCY_SYMBOLS, symbol) >= 0;
		}
	}

}
