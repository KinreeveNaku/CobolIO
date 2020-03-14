package net.sf.cobol2j.translets;

import org.apache.xalan.xsltc.DOM;
import org.apache.xalan.xsltc.TransletException;
import org.apache.xalan.xsltc.dom.StepIterator;
import org.apache.xalan.xsltc.runtime.AbstractTranslet;
import org.apache.xalan.xsltc.runtime.BasisLibrary;
import org.apache.xalan.xsltc.runtime.StringValueHandler;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.serializer.SerializationHandler;
import org.xml.sax.SAXException;

public class cb2xml2cobol2j extends AbstractTranslet {
	private DOM model;
	protected static String[] sNamesArray = new String[14];
	protected static String[] sUrisArray;
	protected static int[] sTypesArray;
	protected static String[] sNamespaceArray;
	protected static char[] scharData0;

	@Override
	public void buildKeys(DOM model, DTMAxisIterator xpathIterator, SerializationHandler handler, int var4)
			throws TransletException {
	}

	public void topLevel(DOM model) {
		model.getIterator().next();
	}

	@Override
	public void transform(DOM model, SerializationHandler[] handlers) throws TransletException {
		this.model = this.makeDOMAdapter(model);
		for (SerializationHandler handler : handlers) {
			this.transferOutputSettings(handler);
			this.topLevel(model);
		}

	}

	@Override
	public void transform(DOM model, DTMAxisIterator xpathIterator, SerializationHandler handler)
			throws TransletException {
		this.model = this.makeDOMAdapter(model);
		this.transferOutputSettings(handler);
		this.topLevel(this.model);
		try {
			handler.startDocument();
			this.applyTemplates(this.model, xpathIterator, handler);
			handler.endDocument();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public void template(DOM var1, DTMAxisIterator xpathIterator, SerializationHandler handler, int var4)
			throws SAXException {
		SerializationHandler handlerA = handler;
		handler.startElement("FileFormat");
		handler.addUniqueAttribute("ConversionTable", "Cp037", 1);
		handler.addUniqueAttribute("newLineSize", "0", 1);
		handler.addUniqueAttribute("dataFileImplementation", "IBM i or z System", 1);
		SerializationHandler handlerC = handler;
		SerializationHandler handlerD = handler;
		StringValueHandler var13;
		StringValueHandler var10005 = var13 = super.stringValueHandler;
		if (BasisLibrary.countF(var1.getTypedAxisIterator(3, 15).setStartNode(var4)) == 1) {
			var13.characters(scharData0, 0, 1);
		} else {
			DTMAxisIterator var5 = var1.getTypedAxisIterator(3, 15);
			DTMAxisIterator var6 = var1.getTypedAxisIterator(3, 15);
			StepIterator var7 = new StepIterator(var5, var6);
			DTMAxisIterator var8 = var1.getTypedAxisIterator(2, 16);
			var1.characters((new StepIterator(var7, var8)).setStartNode(var4).next(), var13);
		}

		handlerD.addUniqueAttribute("distinguishFieldSize", var10005.getValue(), 0);
		handler = handlerC;
		xpathIterator = var1.getTypedAxisIterator(3, 15).setStartNode(var4);

		while ((var4 = xpathIterator.next()) > 0) {
			SerializationHandler handlerE = handler;
			handler.startElement("RecordFormat");
			SerializationHandler handlerF = handler;
			SerializationHandler handlerG = handler;
			StringValueHandler stringHandler = var13 = super.stringValueHandler;
			DTMAxisIterator var9 = var1.getTypedAxisIterator(3, 15);
			DTMAxisIterator var10 = var1.getTypedAxisIterator(2, 17);
			if ((new StepIterator(var9, var10)).setStartNode(var4).next() >= 0) {
				DTMAxisIterator var11 = var1.getTypedAxisIterator(3, 15);
				DTMAxisIterator var12 = var1.getTypedAxisIterator(2, 17);
				var1.characters((new StepIterator(var11, var12)).setStartNode(var4).next(), var13);
			} else {
				var13.characters(scharData0, 1, 1);
			}

			handlerG.addAttribute("distinguishFieldValue", stringHandler.getValue());
			handler = handlerF;
			//stringHandler = var13 = super.stringValueHandler;
			var1.characters(var1.getTypedAxisIterator(2, 18).setStartNode(var4).next(), var13);
			handlerG.addAttribute("cobolRecordName", stringHandler.getValue());
			this.applyTemplates(var1, var1.getTypedAxisIterator(3, 15).setStartNode(var4), handler);
			handlerE.endElement("RecordFormat");
		}

		handlerA.endElement("FileFormat");
	}

	public void fieldslist(DOM var1, DTMAxisIterator var2, SerializationHandler baseHandler, int var4)
			throws SAXException {
		if (var1.getTypedAxisIterator(2, 19).setStartNode(var4).next() < 0) {
			SerializationHandler handlerA;
			SerializationHandler handlerB;
			SerializationHandler handlerC;
			StringValueHandler stringHandler;
			StringValueHandler var5;
			if (var1.getTypedAxisIterator(2, 20).setStartNode(var4).next() < 0
					&& var1.getTypedAxisIterator(2, 21).setStartNode(var4).next() < 0) {
				handlerA = baseHandler;
				baseHandler.startElement("FieldsGroup");
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 18).setStartNode(var4).next(), var5);
				handlerC.addAttribute("Name", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 22).setStartNode(var4).next(), var5);
				handlerC.addAttribute("DependingOn", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				if (var1.getTypedAxisIterator(2, 23).setStartNode(var4).next() >= 0) {
					var1.characters(var1.getTypedAxisIterator(2, 23).setStartNode(var4).next(), var5);
				} else {
					var5.characters(scharData0, 16, 1);
				}

				handlerC.addAttribute("Occurs", stringHandler.getValue());
				baseHandler = handlerB;
				this.applyTemplates(var1, var1.getTypedAxisIterator(3, 15).setStartNode(var4), baseHandler);
				handlerA.endElement("FieldsGroup");
			} else {
				handlerA = baseHandler;
				baseHandler.startElement("FieldFormat");
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 18).setStartNode(var4).next(), var5);
				handlerC.addAttribute("Name", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 22).setStartNode(var4).next(), var5);
				handlerC.addAttribute("DependingOn", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				if (var1.getTypedAxisIterator(2, 23).setStartNode(var4).next() >= 0) {
					var1.characters(var1.getTypedAxisIterator(2, 23).setStartNode(var4).next(), var5);
				} else {
					var5.characters(scharData0, 2, 1);
				}

				label129: {
					handlerC.addAttribute("Occurs", stringHandler.getValue());
					baseHandler = handlerB;
					handlerB = baseHandler;
					handlerC = baseHandler;
					stringHandler = var5 = super.stringValueHandler;
					var1.characters(var1.getTypedAxisIterator(2, 16).setStartNode(var4).next(), var5);
					handlerC.addAttribute("Size", stringHandler.getValue());
					baseHandler = handlerB;
					int var6;
					if (!BasisLibrary.compare(var1.getTypedAxisIterator(2, 24).setStartNode(var4), "true", 0, var1)) {
						var6 = var1.getTypedAxisIterator(2, 21).setStartNode(var4).next();
						if (!BasisLibrary
								.substringF(var6 >= 0 ? var1.getStringValueX(var6) : "", (double) 1, (double) 4)
								.equals("comp")) {
							baseHandler.addAttribute("Type", "X");
							baseHandler.addAttribute("Decimal", "0");
							break label129;
						}
					}

					handlerB = baseHandler;
					handlerC = baseHandler;
					stringHandler = var5 = super.stringValueHandler;
					if (var1.getTypedAxisIterator(2, 25).setStartNode(var4).next() >= 0) {
						var1.characters(var1.getTypedAxisIterator(2, 25).setStartNode(var4).next(), var5);
					} else {
						var5.characters(scharData0, 3, 1);
					}

					handlerC.addAttribute("Decimal", stringHandler.getValue());
					baseHandler = handlerB;
					if (!BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4), "binary", 0, var1)
							&& !BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
									"computational", 0, var1)
							&& !BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
									"computational-4", 0, var1)
							&& !BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
									"computational-5", 0, var1)) {
						if (BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4), "computational-1",
								0, var1)) {
							baseHandler.addAttribute("Type", "1");
							baseHandler.addAttribute("Size", "4");
						} else if (BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
								"computational-2", 0, var1)) {
							baseHandler.addAttribute("Type", "2");
							baseHandler.addAttribute("Size", "8");
						} else if (!BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
								"computational-3", 0, var1)
								&& !BasisLibrary.compare(var1.getTypedAxisIterator(2, 21).setStartNode(var4),
										"packed-decimal", 0, var1)) {
							var6 = var1.getTypedAxisIterator(2, 20).setStartNode(var4).next();
							if ((var6 >= 0 ? var1.getStringValueX(var6) : "").indexOf("COMP-6") >= 0) {
								baseHandler.addAttribute("Type", "6");
							} else {
								baseHandler.addAttribute("Type", "9");
							}
						} else {
							baseHandler.addAttribute("Type", "3");
						}
					} else {
						baseHandler.addAttribute("Type", "B");
						handlerB = baseHandler;
						handlerC = baseHandler;
						stringHandler = var5 = super.stringValueHandler;
						if (BasisLibrary.compare(var1.getTypedAxisIterator(2, 16).setStartNode(var4), (double) 5, 3,
								var1)) {
							var5.characters(scharData0, 4, 1);
						} else if (BasisLibrary.compare(var1.getTypedAxisIterator(2, 16).setStartNode(var4),
								(double) 10, 3, var1)) {
							var5.characters(scharData0, 5, 1);
						} else {
							var5.characters(scharData0, 6, 1);
						}

						handlerC.addAttribute("Size", stringHandler.getValue());
						baseHandler = handlerB;
					}
				}

				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				if (var1.getTypedAxisIterator(2, 26).setStartNode(var4).next() >= 0) {
					var1.characters(var1.getTypedAxisIterator(2, 26).setStartNode(var4).next(), var5);
				} else {
					var5.characters(scharData0, 7, 5);
				}

				handlerC.addAttribute("Signed", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				if (var1.getTypedAxisIterator(2, 27).setStartNode(var4).next() >= 0) {
					this.characters(
							var1.getTypedAxisIterator(2, 27).setStartNode(var4).next() >= 0 ^ true ? "true" : "false",
							var5);
				} else {
					var5.characters(scharData0, 12, 4);
				}

				handlerC.addAttribute("ImpliedDecimal", stringHandler.getValue());
				baseHandler = handlerB;
				handlerB = baseHandler;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 17).setStartNode(var4).next(), var5);
				handlerC.addAttribute("Value", stringHandler.getValue());
				baseHandler = handlerB;
				handlerC = baseHandler;
				stringHandler = var5 = super.stringValueHandler;
				var1.characters(var1.getTypedAxisIterator(2, 20).setStartNode(var4).next(), var5);
				handlerC.addAttribute("Picture", stringHandler.getValue());
				handlerA.endElement("FieldFormat");
			}
		}

	}

	public final void applyTemplates(DOM var1, DTMAxisIterator var2, SerializationHandler var3) throws SAXException {
		int var4;
		while ((var4 = var2.next()) >= 0) {
			switch (var1.getExpandedTypeID(var4)) {
			case 2:
			case 3:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
				var1.characters(var4, var3);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			default:
				break;
			case 14:
				if (var1.getExpandedTypeID(var1.getParent(var4)) == 9) {
					this.template(var1, var2, var3, var4);
					break;
				}
			case 0:
			case 1:
			case 9:
				this.applyTemplates(var1, var1.getChildren(var4), var3);
				break;
			case 15:
				this.fieldslist(var1, var2, var3, var4);
			}
		}

	}

	static {
		sNamesArray[0] = "copybook";
		sNamesArray[1] = "item";
		sNamesArray[2] = "display-length";
		sNamesArray[3] = "value";
		sNamesArray[4] = "name";
		sNamesArray[5] = "redefined";
		sNamesArray[6] = "picture";
		sNamesArray[7] = "usage";
		sNamesArray[8] = "depending-on";
		sNamesArray[9] = "occurs";
		sNamesArray[10] = "numeric";
		sNamesArray[11] = "scale";
		sNamesArray[12] = "signed";
		sNamesArray[13] = "insert-decimal-point";
		sUrisArray = new String[14];
		sUrisArray[0] = null;
		sUrisArray[1] = null;
		sUrisArray[2] = null;
		sUrisArray[3] = null;
		sUrisArray[4] = null;
		sUrisArray[5] = null;
		sUrisArray[6] = null;
		sUrisArray[7] = null;
		sUrisArray[8] = null;
		sUrisArray[9] = null;
		sUrisArray[10] = null;
		sUrisArray[11] = null;
		sUrisArray[12] = null;
		sUrisArray[13] = null;
		sTypesArray = new int[14];
		sTypesArray[0] = 1;
		sTypesArray[1] = 1;
		sTypesArray[2] = 2;
		sTypesArray[3] = 2;
		sTypesArray[4] = 2;
		sTypesArray[5] = 2;
		sTypesArray[6] = 2;
		sTypesArray[7] = 2;
		sTypesArray[8] = 2;
		sTypesArray[9] = 2;
		sTypesArray[10] = 2;
		sTypesArray[11] = 2;
		sTypesArray[12] = 2;
		sTypesArray[13] = 2;
		sNamespaceArray = new String[0];
		scharData0 = "0010248falsetrue1".toCharArray();
	}

	public cb2xml2cobol2j() {
		super.namesArray = sNamesArray;
		super.urisArray = sUrisArray;
		super.typesArray = sTypesArray;
		super.namespaceArray = sNamespaceArray;
		super.transletVersion = 101;
	}

	public final DOM getModel() {
		return this.model;
	}

	public final void setModel(DOM model) {
		this.model = model;
	}
}