package ks51team03.pet.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class Pet {
	 private String petCode;
	 private String memberId;
	 private String petName;
	 private String petGender;
	 private String petBirth;
	 private String petClass;
	 private String petSpecies;
	 private String petBreed;
	 private String petWeight;
	 private int petNeuter;
	 private int petOperation;
	 private String petMedicine;
	 private String petNote;
	 private String petUrl;
	 private String petRegistDate;
	 
}
