package model;

import java.sql.Date;

public class Fidelite {
    private int idFidelite;
    private int idClient;
    private int points;
    private boolean reductionApplicable;
    private String source;
    private Date dateOperation;
    private String description;

    public Fidelite() {}

    public Fidelite(int idFidelite, int idClient, int points, boolean reductionApplicable,
                    String source, Date dateOperation, String description) {
        this.idFidelite = idFidelite;
        this.idClient = idClient;
        this.points = points;
        this.reductionApplicable = reductionApplicable;
        this.source = source;
        this.dateOperation = dateOperation;
        this.description = description;
    }

    public int getIdFidelite() { return idFidelite; }
    public int getIdClient() { return idClient; }
    public int getPoints() { return points; }
    public boolean isReductionApplicable() { return reductionApplicable; }
    public String getSource() { return source; }
    public Date getDateOperation() { return dateOperation; }
    public String getDescription() { return description; }

    public void setIdFidelite(int idFidelite) { this.idFidelite = idFidelite; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public void setPoints(int points) { this.points = points; }
    public void setReductionApplicable(boolean reductionApplicable) { this.reductionApplicable = reductionApplicable; }
    public void setSource(String source) { this.source = source; }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }
    public void setDescription(String description) { this.description = description; }

}
