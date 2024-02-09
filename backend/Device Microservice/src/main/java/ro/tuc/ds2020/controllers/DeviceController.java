package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.facade.DeviceFacade;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin( origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600, allowCredentials = "true")
@RequestMapping(value = "api/devices")
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class DeviceController {

    private final DeviceFacade deviceFacade;

    @Autowired
    public DeviceController(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable("id") Long userId) {
        List<DeviceDTO> dtos = deviceFacade.findByUserId(userId);

        for(DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");

            dto.add(deviceLink);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") Long deviceId) {
        Optional<DeviceDTO> dto = deviceFacade.findById(deviceId);

        return dto.map(deviceDTO -> new ResponseEntity<>(deviceDTO, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceFacade.findAll();

        for(DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");

            dto.add(deviceLink);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DeviceDTO> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        Optional<DeviceDTO> dto = deviceFacade.save(deviceDTO);

        return dto.map(deviceDTO1 -> new ResponseEntity<>(deviceDTO1, HttpStatus.CREATED)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateDevice(@PathVariable("id") Long deviceId, @RequestBody DeviceDTO deviceDTO) {
        deviceDTO.setId(deviceId);
        boolean updated = deviceFacade.update(deviceDTO);

        if(updated) {
            return new ResponseEntity<>(deviceId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Long deviceId) {
        boolean deleted = deviceFacade.delete(deviceId);

        if(deleted) {
            return new ResponseEntity<>(deviceId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}/delete")
    public ResponseEntity<?> deleteAllDevicesByUserId(@PathVariable("id") Long userId) {
        boolean deleted = deviceFacade.deleteAllByUserId(userId);

        if(deleted) {
            return new ResponseEntity<>(userId, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
