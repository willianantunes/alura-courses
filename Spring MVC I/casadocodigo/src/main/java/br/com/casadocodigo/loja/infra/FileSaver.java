package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.casadocodigo.loja.exception.PastaFileSaverNaoPodeSerCriadaException;

@Component
public class FileSaver {
	@Autowired
	private HttpServletRequest request;
	private String baseFolder = "arquivos-sumario";

	public String write(MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			
			criaPastaSeNaoExistir(realPath);
			
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));

			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void criaPastaSeNaoExistir(String realPath) {
		Path myPath = Paths.get(realPath);
		if (!Files.exists(myPath))
			try {
				Files.createDirectory(myPath);
			} catch (IOException e) {
				throw new PastaFileSaverNaoPodeSerCriadaException(e);
			}
	}
}