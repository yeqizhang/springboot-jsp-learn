package com.tgc.test;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 测试怎么扫描的Controller等注解
 * @author Administrator
 *
 */
public class BeanFactoryTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		// 获取指定包下的所有类 public interface Resource extends InputStreamSource
		// ，这里只是获取文件资源集合
		Resource[] resources = resourcePatternResolver.getResources("classpath*:com\\tgc\\controller\\*");
		/*
		 * ClassMetadata——提供对class类的信息访问。
		 * 类元数据模型在包org.springframework.core.type下，是spring对class文件的描述单元，
		 * 包含ClassMetadata，MethodMetadata，AnnotationMetadata等元数据，
		 * 都是对外提供对class属性的访问，同时这些元数据是通过ASM字节码框架解析字节码获取生成。
		 */
		MetadataReaderFactory metadata = new SimpleMetadataReaderFactory();
		Set<BeanDefinition> candidates = new LinkedHashSet<>(); // bean定义信息集合
		for (Resource resource : resources) {
			System.out.println("r:" + resource);
			MetadataReader metadataReader = metadata.getMetadataReader(resource); // 类元数据读取工厂根据资源获取到class的信息。
			ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
			sbd.setResource(resource);
			sbd.setSource(resource);
			candidates.add(sbd);
		}
		for (BeanDefinition beanDefinition : candidates) {
			String classname = beanDefinition.getBeanClassName(); // 类的全名
			// 扫描（反射该类）是否使用了controller注解
			Controller c = Class.forName(classname).getAnnotation(Controller.class);
			// 扫描是否使用了Service注解
			Service s = Class.forName(classname).getAnnotation(Service.class);
			// 扫描是否使用了Component注解
			Component component = Class.forName(classname).getAnnotation(Component.class);
			if (c != null || s != null || component != null) {
				System.out.println(classname);
			}
		}
	}
}