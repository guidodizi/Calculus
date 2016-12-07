package calculus.backend.service;

import calculus.backend.model.AbstractModel;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AbstractService")
//@Transactional
public abstract class AbstractService<T extends AbstractModel> {
    
    protected abstract JpaRepository getRepository();
    
    
    
    @Transactional
    public List<T> findAll() {
        return this.getRepository().findAll();
    }
    
    public List<T> findAll(Collection<Long> ids){
        return this.getRepository().findAll(ids);
        
    }
    
    @Transactional
    public T findById(long id) {
        return (T) this.getRepository().findOne(id);
    }
    
    @Transactional
    public T create(T obj) {
        return (T) this.getRepository().save(obj);
    }
    
    @Transactional
    public T update(T obj) {
        return (T) this.getRepository().save(obj);
    }
    
    @Transactional
    public void delete(T obj) {
        this.getRepository().delete(obj);
    }
    
    @Transactional
    public void deleteId(Long id) {
        this.getRepository().delete(id);
    }
    
    @Transactional
    public void deleteAll(){
        this.getRepository().deleteAll();
    }
    
    @Transactional
    public boolean exists(T obj) {
        return this.getRepository().exists(obj.getId());
    }
    
    @Transactional
    public boolean existsId(Long id) {
        return this.getRepository().exists(id);
    }
    
    @Transactional
    protected String getGenericName() {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }
    
    @Transactional
    protected Class<T> getEntityClass(){
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
}