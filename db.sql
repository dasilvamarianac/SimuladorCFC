CREATE TABLE IF NOT EXISTS `a9188182_cfc`.`Questao` (
  `idQuestao` INT NOT NULL AUTO_INCREMENT,
  `enunciado` VARCHAR(500) NOT NULL,
  `alternativa1` VARCHAR(500) NOT NULL,
  `alternativa2` VARCHAR(500) NOT NULL,
  `alternativa3` VARCHAR(500) NOT NULL,
  `alternativa4` VARCHAR(500) NOT NULL,
  `correta` INT NOT NULL,
  `imagem` VARCHAR(300) NULL,
  PRIMARY KEY (`idQuestao`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `a9188182_cfc`.`Simulacao` (
  `idSimulacao` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` INT NOT NULL,
  `acertos` INT NOT NULL,
  PRIMARY KEY (`idSimulacao`, `idUsuario`),
  INDEX `idUser_idx` (`idUsuario` ASC),
  CONSTRAINT `idUser`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `a9188182_cfc`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `a9188182_cfc`.`Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(150) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB