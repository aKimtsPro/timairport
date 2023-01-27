package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.exceptions.RessourceNotFoundException;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import be.technifutur.java.timairport.repository.CompanyRepository;
import be.technifutur.java.timairport.repository.PlaneRepository;
import be.technifutur.java.timairport.repository.TypePlaneRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;
    private final CompanyRepository companyRepository;
    private final TypePlaneRepository typePlaneRepository;

    public PlaneServiceImpl(
            PlaneRepository planeRepository,
            CompanyRepository companyRepository,
            TypePlaneRepository typePlaneRepository
    ) {
        this.planeRepository = planeRepository;
        this.companyRepository = companyRepository;
        this.typePlaneRepository = typePlaneRepository;
    }

    @Override
    public void create(PlaneInsertForm form) {

        Plane plane = new Plane();

        plane.setCallSign( form.getCallSign() );
        plane.setRegistrationDate( form.getRegistrationDate() );

        Company company = companyRepository.findById( form.getCompanyId() )
                .orElseThrow( RessourceNotFoundException::new );
        TypePlane typePlane = typePlaneRepository.findById( form.getTypeId() )
                .orElseThrow( RessourceNotFoundException::new );

        plane.setCompany( company );
        plane.setType( typePlane );

        planeRepository.save( plane );

    }

}
