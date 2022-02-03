package com.abaco.negocio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;

import com.abaco.ageneral.EOperacionDocumentoRevision;
import com.abaco.ageneral.EOperacionSolicitudCreditoDocumentoRevision;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.ageneral.ESuscripcion;

public class UtilPoi {

	public static void crearTituloCell(HSSFWorkbook wb, Row row, int column, HorizontalAlignment halign,
			VerticalAlignment valign, String strContenido, int iTamanioTitulo) {
		CreationHelper ch = wb.getCreationHelper();
		// Cell cell = row.createCell(column);
		Cell cell = row.createCell(column);
		cell.setCellValue(ch.createRichTextString(strContenido));

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) iTamanioTitulo);
		cellFont.setFontName(HSSFFont.FONT_ARIAL);
		cellFont.setUnderline((byte) 1);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);
	}

	public static void crearCelda(Sheet sheet, int iRow, int iCol, Object objContenido, CellStyle csDescrip) {
		Cell cell = null;

		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.getCell(iCol);

		if (cell == null) {
			cell = row.createCell(iCol);

			sheet.autoSizeColumn(cell.getColumnIndex());
		}

		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		} else if (objContenido instanceof Integer) {
			cell.setCellValue((Integer) objContenido);
		} else if (objContenido instanceof Date) {
			cell.setCellValue((Date) objContenido);
		}
		if (csDescrip != null) {
			cell.setCellStyle(csDescrip);
		}
	}

	public static void crearCeldaConTamaño(XSSFWorkbook wb, Sheet sheet, int iRow, int iCol, Object objContenido, CellStyle csDescrip, int ancho, HorizontalAlignment alignment, boolean esFormatoNumerico) {
		Cell cell = null;
		sheet.setColumnWidth(iCol, 256*ancho);
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.getCell(iCol);

		if (cell == null) {
			cell = row.createCell(iCol);
		}

		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		} else if (objContenido instanceof Long) {
			cell.setCellValue((Long) objContenido);
		} else if (objContenido instanceof Integer) {
			cell.setCellValue((Integer) objContenido);
		} else if (objContenido instanceof Date) {
			cell.setCellValue((Date) objContenido);
		}

		if (csDescrip != null) {
			if(esFormatoNumerico){
				DataFormat format = wb.createDataFormat();
				csDescrip.setDataFormat(format.getFormat("###,###,###,###.00"));
			}
			cell.setCellStyle(csDescrip);
			cell.getCellStyle().setVerticalAlignment(VerticalAlignment.TOP);
			cell.getCellStyle().setAlignment(alignment);

		}
	}

	////CREAR CELDA SXSSFWorkbook
	public static void crearCeldaSXConTamaño(
			SXSSFWorkbook wb, Sheet sheet, int iRow, int iCol,
			Object objContenido, CellStyle csDescrip, int ancho, HorizontalAlignment alignment, boolean esFormatoNumerico) {
		Cell cell = null;

		sheet.setColumnWidth(iCol, 256 * ancho);
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.getCell(iCol);

		if (cell == null) {
			cell = row.createCell(iCol);
		}

		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		} else if (objContenido instanceof Long) {
			cell.setCellValue((Long) objContenido);
		} else if (objContenido instanceof Integer) {
			cell.setCellValue((Integer) objContenido);
		} else if (objContenido instanceof Date) {
			cell.setCellValue((Date) objContenido);
		}

		if (csDescrip != null) {
			if (esFormatoNumerico) {
				DataFormat format = wb.createDataFormat();
				csDescrip.setDataFormat(format.getFormat("###,###,###,##0.00"));
			}
			cell.setCellStyle(csDescrip);
			if (alignment != null) {
				cell.getCellStyle().setVerticalAlignment(VerticalAlignment.TOP);
				cell.getCellStyle().setAlignment(alignment);
			}
		}
	}

    public static void crearCeldaSXConTamaño(
            SXSSFWorkbook wb, Sheet sheet, int iRow, int iCol,
            Object objContenido, CellStyle csDescrip, int ancho) {
        Cell cell = null;

        sheet.setColumnWidth(iCol, 256 * ancho);
        Row row = sheet.getRow(iRow);
        if (row == null)
            row = sheet.createRow(iRow);
        cell = row.getCell(iCol);

        if (cell == null) {
            cell = row.createCell(iCol);
        }

        if (objContenido instanceof String) {
            cell.setCellValue((String) objContenido);
        } else if (objContenido instanceof Double) {
            cell.setCellValue((Double) objContenido);
        } else if (objContenido instanceof Long) {
            cell.setCellValue((Long) objContenido);
        } else if (objContenido instanceof Integer) {
            cell.setCellValue((Integer) objContenido);
        } else if (objContenido instanceof Date) {
            cell.setCellValue((Date) objContenido);
        }

        if (csDescrip != null) {
            cell.setCellStyle(csDescrip);
        }
    }
    
    public static void crearCeldaSXConTamaño2(
            HSSFWorkbook wb, Sheet sheet, int iRow, int iCol,
            Object objContenido, CellStyle csDescrip, int ancho) {
        Cell cell = null;

        sheet.setColumnWidth(iCol, 256 * ancho);
        Row row = sheet.getRow(iRow);
        if (row == null)
            row = sheet.createRow(iRow);
        cell = row.getCell(iCol);

        if (cell == null) {
            cell = row.createCell(iCol);
        }

        if (objContenido instanceof String) {
            cell.setCellValue((String) objContenido);
        } else if (objContenido instanceof Double) {
            cell.setCellValue((Double) objContenido);
        } else if (objContenido instanceof Long) {
            cell.setCellValue((Long) objContenido);
        } else if (objContenido instanceof Integer) {
            cell.setCellValue((Integer) objContenido);
        } else if (objContenido instanceof Date) {
            cell.setCellValue((Date) objContenido);
        }

        if (csDescrip != null) {
            cell.setCellStyle(csDescrip);
        }
    }

	public static void crearCeldaFormula(Sheet sheet, int iRow, int iCol, String strFormula) {
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.createCell(iCol);
		// cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		cell.setCellFormula(strFormula);
	}

	@SuppressWarnings("deprecation")
	public static Double obtenerValorCelda(Sheet sheet, int iRow, int iCol) {
		Double valor = new Double(0);
		Row row = sheet.getRow(iRow);
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					valor = cell.getNumericCellValue();
				}
			}
		}
		return valor;
	}

	@SuppressWarnings("deprecation")
	public static String obtenerValorCeldaString(Sheet sheet, int iRow, int iCol) {
		String valor = null;
		Row row = sheet.getRow(iRow);
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					valor = cell.getStringCellValue();
				}
			}
		}
		return valor;
	}

	public static XSSFWorkbook obtenerArchivoExcel(String fileName) {
		XSSFWorkbook libro = null;
		try {
			libro = new XSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return libro;
	}


	public static SXSSFWorkbook obtenerArchivoExcelGrande(String fileName) {
		SXSSFWorkbook libro = null;
		XSSFWorkbook libroInicial=null;
		try {
			libroInicial = new XSSFWorkbook(new FileInputStream(fileName));
			libro = new SXSSFWorkbook(libroInicial);
			//libro = new SXSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return libro;
	}
	
	public static HSSFWorkbook obtenerArchivoExcelGrande2(String fileName) {
		//SXSSFWorkbook libro = null;
		HSSFWorkbook libroInicial=null;
		try {
			libroInicial = new HSSFWorkbook(new FileInputStream(fileName));
			//libro = new SXSSFWorkbook(libroInicial);
			//libro = new SXSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return libroInicial;
	}

	public static CellStyle obtenerEstilo(Sheet sheet, int iRow, int iCol) {
		CellStyle cellStyle = null;
		try {
			Cell cell = sheet.getRow(iRow).getCell(iCol);
			cellStyle = cell.getCellStyle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellStyle;
	}

	public static void crearCeldaConEstilo(Sheet sheet, int iRow, int iCol, Object objContenido, CellStyle cellStyle) {
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.createCell(iCol);
		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		}
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}

	}

	public static void crearCeldaCombinada(Sheet sheet, int iRow, int iRow2, int iCol, int iCol2, Object objContenido,
			CellStyle csDescrip) {
		for (int i = iRow; i <= iRow2; i++) {
			for (int j = iCol; j <= iCol2; j++) {
				UtilPoi.crearCelda(sheet, i, j, "", csDescrip);
			}
		}
		UtilPoi.crearCelda(sheet, iRow, iCol, objContenido, csDescrip);
		sheet.addMergedRegion(new CellRangeAddress(iRow, iRow2, iCol, iCol2));
	}

	@SuppressWarnings("deprecation")
	public static void evaluarCelda(Sheet sheet, int iRow, int iCol) {
		FormulaEvaluator evaluador = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				evaluador.evaluateFormulaCell(cell);
			}
		}
	}

	public static String generarArchivo_XLSX(XSSFWorkbook wb, String rutaArchivoGenerado) {
		String rutaArchivo = "";
		File strRuta = new File(rutaArchivoGenerado);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}

	public static String generarArchivoGrande_XLSX(SXSSFWorkbook wb, String nombreArchivo) {
		String rutaArchivo = "";
		//String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyy");
		//StringBuilder sbNombre = new StringBuilder();
		//sbNombre.append(nombreArchivo).append("_").append(sufijo).append(".xlsx");

		File strRuta = new File(nombreArchivo);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}
	
	public static String generarArchivoGrande_XLSX2(HSSFWorkbook wb, String nombreArchivo) {
		String rutaArchivo = "";

		File strRuta = new File(nombreArchivo);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}


	public static void eliminarFila(Sheet sheet, int iRow) {
		Row row = sheet.getRow(iRow);
		if (row != null) {
			sheet.removeRow(row);
		}
	}

	public static XWPFDocument obtenerArchivoWord(String fileName) {
		XWPFDocument docWord = null;
		try {
			docWord = new XWPFDocument(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return docWord;
	}

	/**
	 * Ubica un marcador y lo reemplaza por texto
	 *
	 * @param docx
	 *            Documento a buscar
	 * @param buscar
	 *            Marca a buscar
	 * @param reemplazo
	 *            Texto que reemplazo
	 * @return Numero de parrafo
	 */
	public static void reemplazarPalabraenParrafo(XWPFDocument docx, String buscar, String reemplazo) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						for (int k = runs.size() - 1; k > 0; k--) {
							parrafos.get(i).removeRun(k);
						}
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void reemplazarPalabraenParrafoSinBorrado(XWPFDocument docx, String buscar, String reemplazo) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						// for (int k = runs.size()-1; k > 0; k--) {
						// parrafos.get(i).removeRun(k);
						// }
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Ubica un marcador y lo reemplaza por texto
	 *
	 * @param docx
	 *            Documento a buscar
	 * @param buscar
	 *            Marca a buscar
	 * @param reemplazo
	 *            Texto que reemplazo
	 * @return Numero de parrafo
	 */
	public static void reemplazarPalabraenTabla(XWPFDocument docx, String buscar, String reemplazo) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (!cadena.equals("")) {
							// busca en la cadena si hay algun campo que
							// reemplazar
							if (cadena.contains(buscar)) {
								// quito la marca
								int tamanoParrafo = runs.size();
								for (int k = 0; k < tamanoParrafo; k++) {
									xwpfParagraph.removeRun(k);
								}
								// obtengo el valor a reemplazar
								cadena = cadena.replace(buscar, reemplazo);
								XWPFRun run = runs.get(0);
								run.setText(cadena, 0);
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public static String generarArchivoWord(XWPFDocument docx, String rutaLinuxWord) {
		String rutaArchivo = "";
		File strRuta = new File(rutaLinuxWord);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			docx.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}

	public static void eliminarTabla(XWPFDocument docx, String buscar) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			boolean existe = false;
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (UFuncionesGenerales.revisaCadena(cadena).contains(buscar)) {
							existe = true;
							break;
						}
					}
					if (existe) {
						break;
					}
				}
				if (existe) {
					while (xwpfTable.getNumberOfRows() > 0) {
						xwpfTable.removeRow(0);

					}
				}
				existe = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void eliminarFilaTabla(XWPFDocument docx, String buscar) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			boolean existe = false;
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				int row = -1;
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					row +=1;
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (UFuncionesGenerales.revisaCadena(cadena).contains(buscar)) {
							existe = true;
							break;
						}
					}
					if (existe) {
						break;
					}
				}
				if (existe) {
					xwpfTable.removeRow(row);
					break;
				}
				existe = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void saltoPaginaParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try{
		List<XWPFParagraph> parrafos = docx.getParagraphs();
		for (int i = 0; i < parrafos.size(); i++) {
			List<XWPFRun> runs = parrafos.get(i).getRuns();
			String cadena = "";
			for (int j = 0; j < runs.size(); j++) {
				cadena = cadena + runs.get(j);
			}

			if (!cadena.equals("")) {
				// busca en la cadena si hay algun campo que reemplazar
				if (cadena.contains(buscar)) {
					// obtengo el valor a reemplazar
					XWPFRun run = runs.get(0);
					run.setText("   ", 0);
					run.addBreak(BreakType.PAGE);
					// quito la marca
					// int tamanoParrafo = runs.size();
					// for (int k = 0; k < tamanoParrafo; k++) {
					// parrafos.get(i).removeRun(k);
					// }
				}
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void borroParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						deleteParagraph(parrafos.get(i));

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void deleteParagraph(XWPFParagraph p) {
		XWPFDocument doc = p.getDocument();
		int pPos = doc.getPosOfParagraph(p);
		// doc.getDocument().getBody().removeP(pPos);
		doc.removeBodyElement(pPos);
	}

	public static int copiarParrafoWordIniWordFin(XWPFDocument docPartida, XWPFDocument docDestino, int posicion) {

		for (IBodyElement e : docPartida.getBodyElements()) {
			try {
				if (e instanceof XWPFParagraph) {
					XWPFParagraph p = (XWPFParagraph) e;
					if (p.getCTP().getPPr() != null && p.getCTP().getPPr().getSectPr() != null) {
						continue;
					} else {
						docDestino.createParagraph();
						docDestino.setParagraph(p, posicion);
						posicion++;
					}
				} else if (e instanceof XWPFTable) {
					XWPFTable t = (XWPFTable) e;
					docDestino.createTable();
					docDestino.setTable(posicion, t);
					posicion++;
				}
			} catch (Exception ex) {
				// TODO: handle exception
			}
		}

		return posicion;
	}

	public void passaStili(XWPFDocument docPartida, XWPFDocument docDestino) {
		try {
			CTStyles c1 = docPartida.getStyle();
			docDestino.createStyles().setStyles(c1);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int ubica1PosParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		int retorno = 0;
		List<XWPFParagraph> parrafos = docx.getParagraphs();
		for (int i = 0; i < parrafos.size(); i++) {
			List<XWPFRun> runs = parrafos.get(i).getRuns();
			String cadena = "";
			for (int j = 0; j < runs.size(); j++) {
				cadena = cadena + runs.get(j);
			}

			if (!cadena.equals("")) {
				// busca en la cadena si hay algun campo que reemplazar
				if (cadena.contains(buscar)) {
					retorno = i;
					break;
				}
			}
		}
		return retorno;
	}

	@SuppressWarnings({ "deprecation" })
	public static Row copiarFila(XSSFWorkbook workbook, XSSFSheet worksheet, int sourceRowNum, int destinationRowNum, CellStyle csDescrip)
	{
	    // Get the source / new row
	    Row newRow = worksheet.getRow(destinationRowNum);
	    Row sourceRow = worksheet.getRow(sourceRowNum);

	    // If the row exist in destination, push down all rows by 1 else create a new row
	    if (newRow != null)
	    {
	        worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
	    }
	    else
	    {
	        newRow = worksheet.createRow(destinationRowNum);
	    }

	    // Loop through source columns to add to new row
	    for (int i = 0; i < sourceRow.getLastCellNum(); i++)
	    {
	        // Grab a copy of the old/new cell
	        Cell oldCell = sourceRow.getCell(i);
	        Cell newCell = newRow.createCell(i);

	        // If the old cell is null jump to next cell
	        if (oldCell == null)
	        {
	            newCell = null;
	            continue;
	        }

	        // Copy style from old cell and apply to new cell
	        CellStyle newCellStyle = workbook.createCellStyle();
	        newCellStyle.cloneStyleFrom(csDescrip); ;
	        newCell.setCellStyle(newCellStyle);

	        // Set the cell data type
	        newCell.setCellType(oldCell.getCellType());

	        // Set the cell data value
	        switch (oldCell.getCellType())
	        {
	            case HSSFCell.CELL_TYPE_BLANK:
	                newCell.setCellValue(oldCell.getStringCellValue());
	                break;
	            case HSSFCell.CELL_TYPE_FORMULA:
	                newCell.setCellFormula(oldCell.getCellFormula());
	                //Si tenemos que modificar la formulario lo podemos hacer como string
	                //oldCell.getCellFormula().replace("A"+sourceRowNum, "A"+destinationRowNum)
	                break;
	            case HSSFCell.CELL_TYPE_NUMERIC:
	                newCell.setCellValue(oldCell.getNumericCellValue());
	                break;
	            case HSSFCell.CELL_TYPE_STRING:
	                newCell.setCellValue(oldCell.getRichStringCellValue());
	                break;
	        }
	    }
	    //..
	    //..
	    return newRow;
	}

	public static void reemplazarPalabraenTabla2(XWPFDocument docx, String buscar, String reemplazo,boolean bold) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						for (XWPFParagraph p : xwpfTableCell.getParagraphs()) {
						      for (XWPFRun r : p.getRuns()) {
							       String text = r.getText(0);						       
							       if (!text.equals("")) {
							    	   if (text.contains(buscar)) {
							    		   // p.removeRun(0);		
							    		  
									        text = text.replace(buscar, reemplazo);
									       if(text.contains("\n")){
									    	    String[] lines = text.split("\n");
							                    r.setText(lines[0], 0); // set first line into XWPFRun
								                for(int i=1;i<lines.length;i++){
								                    // add break and insert new text
								                    r.addBreak();
								                    r.setText(lines[i]);
								                    r.setBold(bold); 
								                } 
							    		   }else{
							    			   r.setText(text,0);
										       r.setBold(bold); 
							    		   }
									        
									       
									   } 
							       }
						       
						      }
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}
	

	public static XWPFTable crearTablaRepresentanteLegal(XWPFDocument docx, String buscar, List<ERepresentanteLegal> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						//setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden()+"", false);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden() > 0 ? lista.get(0).getCodigoOrden()+"":"-", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCargoLaboral(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getDocumento(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreLargo(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							//setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden()+"", false);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden() > 0 ? lista.get(e).getCodigoOrden()+"":"-", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCargoLaboral()!= null ? lista.get(e).getCargoLaboral(): lista.get(e).getCargoLaboral().equals("") ? "-":"-", false);
							paragraph = tableRowOne.getCell(2).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getDocumento(), false);
							paragraph = tableRowOne.getCell(3).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreLargo(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaRepresentanteLegalAval(XWPFDocument docx, String buscar, List<ERepresentanteLegal> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						//setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden()+"", false);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden() > 0 ? lista.get(0).getCodigoOrden()+"":"-", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCargoLaboral(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getDocumento(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreLargo(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getInscripcionPoder1(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							//setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden()+"", false);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden() > 0 ? lista.get(e).getCodigoOrden()+"":"-", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCargoLaboral()!= null ? lista.get(e).getCargoLaboral(): lista.get(e).getCargoLaboral().equals("") ? "-":"-", false);
							paragraph = tableRowOne.getCell(2).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getDocumento(), false);
							paragraph = tableRowOne.getCell(3).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreLargo(), false);
							paragraph = tableRowOne.getCell(4).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getInscripcionPoder1(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaDocumentosRevisados(XWPFDocument docx, String buscar, List<EOperacionDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaDocumentosFaltantes(XWPFDocument docx, String buscar, List<EOperacionDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	

	public static XWPFTable crearTablaDocumentosRevisados2(XWPFDocument docx, String buscar, List<EOperacionSolicitudCreditoDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaDocumentosFaltantes2(XWPFDocument docx, String buscar, List<EOperacionSolicitudCreditoDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaSuscripcion(XWPFDocument docx, String buscar, List<ESuscripcion> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNumeroSuscripcion()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreLargo(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNumeroSuscripcion()+"", false);
							paragraph = tableRowOne.getCell(2).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreLargo(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFRun setRun(XWPFRun run, String fontFamily, int fontSize, String text, boolean bold) {
		run.setFontFamily(fontFamily);
		run.setFontSize(fontSize);
		run.setText(text);
		run.setBold(bold);
		return run;
	}
	
	public static void reemplazarPalabraenParrafoBold(XWPFDocument docx, String buscar, String reemplazo,boolean bold) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					if(runs.get(j).toString().contains(buscar)){
						String textoReemplazado = "";
						String[] cad = runs.get(j).toString().split(" ");
						for(int m=0;m<cad.length;m++){
							if(cad[m].trim().equals(buscar)){
								textoReemplazado = textoReemplazado +" " + reemplazo;
							}else{
								textoReemplazado = textoReemplazado +" " + cad[m];
							}
							
						}
						runs.get(j).setText(textoReemplazado,0);
						runs.get(j).setBold(bold);
					}
					//cadena = cadena + runs.get(j);
					
				}
				
				/*
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						for (int k = runs.size() - 1; k > 0; k--) {
							parrafos.get(i).removeRun(k);
						}
						
						String cad[]= cadena.split(" ");
                        for(int m=0;m<cad.length;m++){
							if(cad[m].equals(buscar)){
								cadena = cad[m];
							}
						}
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
						run.setBold(bold);
					}
				}*/
			}
		} catch (Exception e) {
		}
	}
	
	public static void reemplazarPalabraenParrafoConSaltoLinea(XWPFDocument docx, String buscar, String reemplazo) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						for (int k = runs.size() - 1; k > 0; k--) {
							parrafos.get(i).removeRun(k);
						}
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						if(cadena.contains("\n")){
				    	    String[] lines = cadena.split("\n");
				    	    XWPFRun run = runs.get(0);
		                    run.setText(lines[0], 0); // set first line into XWPFRun
			                for(int j=1;j<lines.length;j++){
			                    // add break and insert new text
			                    run.addBreak();
			                    run.setText(lines[j]);
			                } 
		    		   }else{
		    			   XWPFRun run = runs.get(0);
		    			   run.setText(cadena, 0);
		    		   }
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
