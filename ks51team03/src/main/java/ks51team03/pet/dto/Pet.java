package ks51team03.pet.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class Pet {
	 private String petCode;
	 private String memberId;
	 private String petName;
	 private String petGender;
	 private String petBirth;
	 private String petClass;		//분류(강):포유루
	 private String petSpecies;		//종:개
	 private String petBreed;		//품종:말티즈
	 private String petWeight;
	 private int petNeuter;			
	 private int petOperation;		
	 private String petMedicine;
	 private String petNote;
	 private String petUrl;
	 private String petRegistDate;
	 
}
