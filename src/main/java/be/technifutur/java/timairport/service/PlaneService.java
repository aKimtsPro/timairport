package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;

import java.util.List;
import java.util.Map;

public interface PlaneService {

    void create(PlaneInsertForm form);

    PlaneDTO getOne(long id);

    List<PlaneDTO> getAll();

    void updateMaintenance( long idPlane, boolean maintenance );

    void updateCompany( long idPlane, long companyId );

    void update(long id, Map<String, Object> updateData);

    void delete(long id);
}
