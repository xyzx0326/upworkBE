package kodlamaio.northwind.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProcessRepository extends JpaRepository<Process, Long> {
//
////
////    Process findByUserIdAndParentProcessAndProcessId(String userId, Long parentProcess, Long processID);
////
////    List<Process> findByUserIdAndParentProcessOrderByProcessIdDesc(String userId, Long parentProcess);
////
////    Process findTopByUserIdAndParentProcessOrderByProcessIdDesc(String userId, Long parentProcess);
////
////    Process findByUserIdAndParentProcess(String userId, Long parentProcess);
////
////    Process findByProcessId(Long processID);
////
////    Process findByUserIdAndProcessId(String userId, Long processID);
////
////    List<Process> findByUserId(String userId);
////
////    @Query("Select e.processId FROM Process e where e.parentProcess =:parentProcess")
////    List<String> findByParentProcess(@Param("parentProcess") Long parentProcess);
////
////    @Query("Select e.processId FROM Process e where e.parentProcess =:parentProcess AND e.userId =:userId ")
////    List<String> getResponseListByParentProcessAndUserId(@Param("parentProcess") Long parentProcess, @Param("userId") String userId);
////
////    @Query("SELECT COUNT(e.processId) AS count FROM Process e WHERE e.parentProcess = :parentProcess AND e.status <> 'DONE'")
////    int getRemainingProcessCount(@Param("parentProcess") Long parentProcess);
//
//}
