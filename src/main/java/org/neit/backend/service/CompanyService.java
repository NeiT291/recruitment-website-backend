package org.neit.backend.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.User;
import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.neit.backend.mapper.CompanyMapper;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final ResultPaginationMapper resultPaginationMapper;
    @Value("${upload.path.company.avatar}")
    private String UPLOAD_PATH_COMPANY_AVATAR;
    @Value("${upload.path.company.banner}")
    private String UPLOAD_PATH_COMPANY_BANNER;
    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, ResultPaginationMapper resultPaginationMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.resultPaginationMapper = resultPaginationMapper;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public CompanyResponse create(CompanyRequest request){
        Company company = companyMapper.toCompany(request);
        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public CompanyResponse update(Integer id, CompanyRequest request){

        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        companyMapper.updateCompany(company, request);
        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer id){
        companyRepository.deleteById(id);
    }
    public ResultPaginationResponse getByName(String request, Optional<String> page, Optional<String> pageSize){
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);

        String[] words = request.split(" ");
        request = String.join(" ", words);

        Page<Company> companyPage = companyRepository.findByNameIgnoreCaseContaining(request, pageable);

        return resultPaginationMapper.toResultPaginationResponse(companyPage);
    }
    public ResultPaginationResponse getAll(Optional<String> page, Optional<String> pageSize){
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        return resultPaginationMapper.toResultPaginationResponse(companyPage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void uploadAvatar(Integer id, MultipartFile file) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        String uploadAvatarPath = UPLOAD_PATH_COMPANY_AVATAR + company.getId() + "\\";

        File avatarFolder = new File(uploadAvatarPath);
        if (!avatarFolder.exists()){
            avatarFolder.mkdirs();
        }

        if(company.getLogoPath() != null && !company.getLogoPath().isEmpty()){
            FileUtils.cleanDirectory(avatarFolder);
        }

        File uploadFile = new File(uploadAvatarPath + file.getOriginalFilename());
        file.transferTo(uploadFile);

        if (!uploadFile.exists() || uploadFile.isDirectory()){
            throw new AppException(ErrorCode.CANNOT_UPLOAD_IMAGE);
        }

        company.setLogoPath(company.getId() + "\\\\" +file.getOriginalFilename());
        companyRepository.save(company);
    }

    public byte[] getAvatar(Integer id) throws IOException {

        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        byte[] image = null;
        try {
            image = Files.readAllBytes(new File(UPLOAD_PATH_COMPANY_AVATAR + company.getLogoPath()).toPath());
        } catch (IOException e) {
            image = Files.readAllBytes(new File("src/main/resources/data/default_company_logo.png").toPath());
        }

        return image;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void uploadBanner(Integer id, MultipartFile file) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));
        String uploadBannerPath = UPLOAD_PATH_COMPANY_BANNER + company.getId() + "\\";

        File bannerFolder = new File(uploadBannerPath);
        if (!bannerFolder.exists()){
            bannerFolder.mkdirs();
        }

        if(company.getBannerPath() != null && !company.getBannerPath().isEmpty()){
            FileUtils.cleanDirectory(bannerFolder);
        }

        File uploadFile = new File(uploadBannerPath + file.getOriginalFilename());
        file.transferTo(uploadFile);

        if (!uploadFile.exists() || uploadFile.isDirectory()){
            throw new AppException(ErrorCode.CANNOT_UPLOAD_IMAGE);
        }

        company.setBannerPath(company.getId() + "\\\\" +file.getOriginalFilename());
        companyRepository.save(company);
    }
    public byte[] getBanner(Integer id) throws IOException {

        Company company = companyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND));

        byte[] image = null;
        try {
            image = Files.readAllBytes(new File(UPLOAD_PATH_COMPANY_BANNER + company.getBannerPath()).toPath());
        } catch (IOException e) {
            image = Files.readAllBytes(new File("src/main/resources/data/avatar-default.png").toPath());
        }

        return image;
    }
}
