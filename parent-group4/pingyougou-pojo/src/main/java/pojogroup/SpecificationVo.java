package pojogroup;

import com.itheima.core.pojo.specification.Specification;
import com.itheima.core.pojo.specification.SpecificationCheck;
import com.itheima.core.pojo.specification.SpecificationOption;

import java.io.Serializable;
import java.util.List;

public class SpecificationVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private SpecificationCheck specificationCheck;
    private List<SpecificationOption> specificationOptionList;  // 因为是一对多关系,所以使用的是list集合来表示


    public SpecificationCheck getSpecificationCheck() {
        return specificationCheck;
    }

    public void setSpecificationCheck(SpecificationCheck specificationCheck) {
        this.specificationCheck = specificationCheck;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
