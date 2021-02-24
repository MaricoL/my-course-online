package com.course.server.dto;

<#list javaTypeSet as javaType>
    <#if javaType == 'Date'>
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
    </#if>
    <#if javaType == 'BigDecimal'>
import java.math.BigDecimal;
    </#if>
</#list>

public class ${Domain}Dto {

    <#-- 声明各个字段 -->
    <#list fieldList as field>
        /**
         * ${field.comment}
         */
        <#if field.javaType == 'Date'>
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        </#if>
        private ${field.javaType} ${field.nameHump};
    </#list>

    <#-- getter setter 方法-->
    <#list fieldList as field>
        public ${field.javaType} get${field.nameBigHump}() {
            return ${field.nameHump};
        }

        public void set${field.nameBigHump}(${field.javaType} ${field.nameHump}) {
            this.${field.nameHump} = ${field.nameHump};
        }
    </#list>


    <#-- toString()方法-->
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
    <#list fieldList as field>
        sb.append(", ${field.nameHump}=").append(${field.nameHump});
    </#list>
        sb.append("]");
        return sb.toString();
    }
}