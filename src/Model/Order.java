
package Model;

public class Order {
    private String name;
    private Integer ips, gpu, mb, ram, psu, sum;

    public Order(String name, Integer ips, Integer gpu, Integer mb, Integer ram, Integer psu) {
        this.name = name;
        this.ips = ips;
        this.gpu = gpu;
        this.mb = mb;
        this.ram = ram;
        this.psu = psu;
        
        sum = 4999*ips + 9999*gpu + 2999*mb + 1350*ram + 1499*psu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIps() {
        return ips;
    }

    public void setIps(Integer ips) {
        this.ips = ips;
    }

    public Integer getGpu() {
        return gpu;
    }

    public void setGpu(Integer gpu) {
        this.gpu = gpu;
    }

    public Integer getMb() {
        return mb;
    }

    public void setMb(Integer mb) {
        this.mb = mb;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getPsu() {
        return psu;
    }

    public void setPsu(Integer psu) {
        this.psu = psu;
    }

    public Integer getSum() {
        sum = 4999*getIps() + 9999*getGpu() + 2999*getMb() + 1350*getRam() + 1499*getPsu();
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
    
    

    
    
    
    
}
