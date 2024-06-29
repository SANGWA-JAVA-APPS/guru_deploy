package com.deploy.repository;

//import com.swaggerjwt.models.Mdl_DeployDetails;
import com.deploy.models.Mdl_DeployDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo_DeployDetails extends JpaRepository<Mdl_DeployDetails, Long> {
    
    @Query(value = "select * from gurudeploy_details dd where dd.status = ?",nativeQuery = true)
    List<Mdl_DeployDetails> getActiveProjects(String status);
    
    @Query(value = "select * from gurudeploy_details dd where port = ? ",nativeQuery = true)
    Mdl_DeployDetails getPort(int port);

    long countByPort(int port);

    @Query(value = "select count(project_final_name)  proj from  gurudeploy_details where project_final_name=? ", nativeQuery = true)
    long countByProjectFinalName(String final_name);

}
