package be.technifutur.java.timairport.service.impl;

import be.technifutur.java.timairport.exceptions.RessourceNotFoundException;
import be.technifutur.java.timairport.mapper.PlaneMapper;
import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import be.technifutur.java.timairport.repository.CompanyRepository;
import be.technifutur.java.timairport.repository.PlaneRepository;
import be.technifutur.java.timairport.repository.TypePlaneRepository;
import be.technifutur.java.timairport.service.PlaneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;
    private final CompanyRepository companyRepository;
    private final TypePlaneRepository typePlaneRepository;
//    private final PlaneMapper mapper;

    public PlaneServiceImpl(
            PlaneRepository planeRepository,
            CompanyRepository companyRepository,
            TypePlaneRepository typePlaneRepository
//            PlaneMapper mapper
    ) {
        this.planeRepository = planeRepository;
        this.companyRepository = companyRepository;
        this.typePlaneRepository = typePlaneRepository;
//        this.mapper = mapper;
    }

    @Override
    public void create(PlaneInsertForm form) {

        Plane plane = form.toEntity();

        Company company = companyRepository.findById( form.getCompanyId() )
                .orElseThrow( RessourceNotFoundException::new );
        TypePlane typePlane = typePlaneRepository.findById( form.getTypeId() )
                .orElseThrow( RessourceNotFoundException::new );

        plane.setCompany( company );
        plane.setType( typePlane );

        planeRepository.save( plane );

    }

    @Override
    public PlaneDTO getOne(long id) {
        return planeRepository.findById(id)
                .map( PlaneDTO::from )
                .orElseThrow( RessourceNotFoundException::new );
    }

    @Override
    public List<PlaneDTO> getAll() {
        return planeRepository.findAll().stream()
                .map( PlaneDTO::from )
                .toList();
    }

    @Override
    public void updateMaintenance(long idPlane, boolean maintenance) {
        if( !planeRepository.existsById(idPlane) ) {
            throw new RessourceNotFoundException();
        }

        planeRepository.updateMaintenance(idPlane, maintenance);
    }

    @Override
    public void updateCompany(long idPlane, long companyId) {
        if( !planeRepository.existsById(idPlane) ) {
            throw new RessourceNotFoundException();
        }

        Company company = companyRepository.findById(companyId)
                        .orElseThrow( RessourceNotFoundException::new );

        planeRepository.updateCompany(idPlane, company);
    }

    public void update(long idPlane, Map<String, Object> updateData){
        if( updateData == null || updateData.isEmpty() )
            return;

        Plane plane = planeRepository.findById( idPlane )
                .orElseThrow( RessourceNotFoundException::new );

        if( updateData.containsKey("companyId") ){
            long companyId = (long)updateData.get("companyId");
            Company company = companyRepository.findById( companyId )
                    .orElseThrow( RessourceNotFoundException::new );
            plane.setCompany( company );
        }

        if( updateData.containsKey("inMaintenance") ){
            plane.setInMaintenance( (boolean)updateData.get("inMaintenance") );
        }

        planeRepository.save(plane);

    }

    @Override
    public void delete(long id) {
        planeRepository.deleteById(id);
    }

}
