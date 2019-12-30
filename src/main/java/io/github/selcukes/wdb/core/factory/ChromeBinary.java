package io.github.selcukes.wdb.core.factory;

import io.github.selcukes.wdb.core.MirrorUrls;
import io.github.selcukes.wdb.enums.OSType;
import io.github.selcukes.wdb.enums.TargetArch;
import io.github.selcukes.wdb.exception.WebDriverBinaryException;
import io.github.selcukes.wdb.util.BinaryDownloadUtil;
import io.github.selcukes.wdb.util.OptionalUtil;
import io.github.selcukes.wdb.util.Platform;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import static io.github.selcukes.wdb.util.OptionalUtil.unwrap;

public class ChromeBinary extends AbstractBinary {
    private static final String BINARY_DOWNLOAD_URL_PATTERN = "%s/%s/chromedriver_%s.zip";

    @Override
    public URL getDownloadURL() {
        try {
            return new URL(String.format(
                BINARY_DOWNLOAD_URL_PATTERN,
                MirrorUrls.CHROMEDRIVER_URL,
                getBinaryVersion(),
                getBinaryEnvironment().getOsNameAndArch()));

        } catch (MalformedURLException e) {
            throw new WebDriverBinaryException(e);
        }
    }

    @Override
    public Platform getBinaryEnvironment() {
        Platform platform = Platform.getPlatform();
        if (getTargetArch().isPresent())
            platform.setArchitecture(unwrap(getTargetArch()).getValue());
        else if (Objects.equals(platform.getOSType(), OSType.WIN))
            platform.setArchitecture(TargetArch.X32.getValue());
        return platform;
    }

    @Override
    public String getBinaryDriverName() {
        return "chromedriver";
    }

    @Override
    protected String getLatestRelease() {
        try {
            return BinaryDownloadUtil.downloadAndReadFile(new URL(MirrorUrls.CHROMEDRIVER_LATEST_RELEASE_URL));
        } catch (MalformedURLException e) {
            throw new WebDriverBinaryException(e);
        }
    }
}