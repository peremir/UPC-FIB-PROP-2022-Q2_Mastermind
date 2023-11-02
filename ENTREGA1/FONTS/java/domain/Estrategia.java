package domain;

import java.util.List;

import domain.Codi.Colors;

public interface Estrategia {

    public List<Colors> jugarTornMaquina(List<Colors> cm, List<Colors> cb) throws Exception;

}