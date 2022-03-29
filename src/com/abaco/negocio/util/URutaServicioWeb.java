package com.abaco.negocio.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import kotlin.text.Regex;

import org.jasypt.util.text.BasicTextEncryptor;
import org.primefaces.context.RequestContext;

import com.abaco.ageneral.EOperacionSolicitudCreditoDocumentoRequerido;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.ENombre;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.UTipoMensaje;
import com.abaco.servicio.laserfiche.Mensaje;

public class URutaServicioWeb {
	
	public static String obtenerRutaAbsoluta(){
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		return realPath;
		}
}
